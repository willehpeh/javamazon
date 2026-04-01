package tech.reactiv.ecommerce.shared.mediator;

public interface Mediator {
    void send(Command command);
    <R> R send(Query<R> query);
}
