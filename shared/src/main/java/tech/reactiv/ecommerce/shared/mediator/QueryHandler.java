package tech.reactiv.ecommerce.shared.mediator;

public interface QueryHandler<Q extends Query<R>, R> {
    R handle(Q query);
}
