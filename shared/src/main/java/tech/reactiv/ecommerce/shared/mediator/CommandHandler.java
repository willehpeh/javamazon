package tech.reactiv.ecommerce.shared.mediator;

public interface CommandHandler<C extends Command<R>, R> {
    R handle(C command);
}
