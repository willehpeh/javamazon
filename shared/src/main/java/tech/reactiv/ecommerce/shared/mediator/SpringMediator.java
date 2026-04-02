package tech.reactiv.ecommerce.shared.mediator;

import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        var handler = commandHandlerFor(command);
        if (handler == null) {
            throw new IllegalStateException("No handler found for command " + command.getClass().getName());
        }
        handler.handle(command);
    }

    @Override
    public <R> R query(Query<R> query) {
        var handler = queryHandlerFor(query);
        if (handler == null) {
            throw new IllegalStateException("No handler found for query " + query.getClass().getName());
        }
        return handler.handle(query);
    }

    private boolean commandHandlerAlreadyRegisteredFor(Class<?> command) {
        return commandHandlers.containsKey(command);
    }

    private boolean queryHandlerAlreadyRegisteredFor(Class<?> query) {
        return queryHandlers.containsKey(query);
    }

    @SuppressWarnings("unchecked")
    private <R> QueryHandler<Query<R>, R> queryHandlerFor(Query<R> query) {
        return (QueryHandler<Query<R>, R>) queryHandlers.get(query.getClass());
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
        return (CommandHandler<Command>) commandHandlers.get(command.getClass());
    }

    private boolean isHandlerType(Type i, Class<?> handlerType) {
        return i instanceof ParameterizedType pt && pt.getRawType() == handlerType;
    }
}
