package tech.reactiv.ecommerce.shared.mediator;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.context.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

@Component
public class SpringMediator implements Mediator {

    private final Map<Class<?>, CommandHandler<?>> commandHandlers = new HashMap<>();
    private final Map<Class<?>, QueryHandler<?, ?>> queryHandlers = new HashMap<>();

    public SpringMediator(List<CommandHandler<?>> commandHandlers, List<QueryHandler<?, ?>> queryHandlers) {
        commandHandlers.forEach(handler -> {
            var command = handledTypeFor(handler, CommandHandler.class);
            if (commandHandlerAlreadyRegisteredFor(command)) {
                throw new IllegalStateException("Duplicate command handler for " + command.getName());
            }
            this.commandHandlers.put(command, handler);
        });
        queryHandlers.forEach(handler -> {
            Class<?> query = handledTypeFor(handler, QueryHandler.class);
            if (queryHandlerAlreadyRegisteredFor(query)) {
                throw new IllegalStateException("Duplicate query handler for " + query.getName());
            }
            this.queryHandlers.put(query, handler);
        });
    }

    private static Class<?> firstTypeArgument(Type i) {
        return (Class<?>) ((ParameterizedType) i).getActualTypeArguments()[0];
    }

    private static Stream<Type> genericInterfacesOn(Object handler) {
        return Arrays.stream(handler.getClass().getGenericInterfaces());
    }

    @Override
    public void command(Command command) {
        traced(command.getClass().getSimpleName(),
                () -> commandHandlerFor(command).handle(command));
    }

    @Override
    public <R> R query(Query<R> query) {
        return traced(query.getClass().getSimpleName(), "query",
                () -> queryHandlerFor(query).handle(query));
    }

    private void traced(String name, Runnable action) {
        traced(name, "command", () -> { action.run(); return null; });
    }

    private <R> R traced(String name, String type, Callable<R> action) {
        var span = GlobalOpenTelemetry.getTracer("mediator").spanBuilder(name)
                .setAttribute("handler.type", type)
                .startSpan();
        try (Scope _ = span.makeCurrent()) {
            return action.call();
        } catch (Exception e) {
            span.setStatus(StatusCode.ERROR, e.getMessage());
            span.recordException(e);
            throw e instanceof RuntimeException re ? re : new RuntimeException(e);
        } finally {
            span.end();
        }
    }

    private boolean commandHandlerAlreadyRegisteredFor(Class<?> command) {
        return commandHandlers.containsKey(command);
    }

    private boolean queryHandlerAlreadyRegisteredFor(Class<?> query) {
        return queryHandlers.containsKey(query);
    }

    @SuppressWarnings("unchecked")
    private <R> QueryHandler<Query<R>, R> queryHandlerFor(Query<R> query) {
        var handler = (QueryHandler<Query<R>, R>) queryHandlers.get(query.getClass());
        if (handler == null) {
            throw new IllegalStateException("No handler found for query " + query.getClass().getName());
        }
        return handler;
    }

    private Class<?> handledTypeFor(Object handler, Class<?> handlerType) {
        return genericInterfacesOn(handler)
                .filter(i -> isHandlerType(i, handlerType))
                .map(SpringMediator::firstTypeArgument)
                .findFirst()
                .orElseThrow();
    }

    @SuppressWarnings("unchecked")
    private CommandHandler<Command> commandHandlerFor(Command command) {
        var handler = (CommandHandler<Command>) commandHandlers.get(command.getClass());
        if (handler == null) {
            throw new IllegalStateException("No handler found for command " + command.getClass().getName());
        }
        return handler;
    }

    private boolean isHandlerType(Type i, Class<?> handlerType) {
        return i instanceof ParameterizedType pt && pt.getRawType() == handlerType;
    }
}
