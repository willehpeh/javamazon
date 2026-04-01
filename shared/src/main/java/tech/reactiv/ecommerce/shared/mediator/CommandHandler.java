package tech.reactiv.ecommerce.shared.mediator;

public interface CommandHandler<C extends Command> {
    void handle(C command);
}