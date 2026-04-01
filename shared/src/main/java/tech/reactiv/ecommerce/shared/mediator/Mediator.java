package tech.reactiv.ecommerce.shared.mediator;

public interface Mediator {
    void command(Command command);
    <R> R query(Query<R> query);
}
