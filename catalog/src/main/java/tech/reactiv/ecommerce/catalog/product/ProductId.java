package tech.reactiv.ecommerce.catalog.product;

import java.util.UUID;

public final class ProductId {

    private final UUID id;

    private ProductId(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        this.id = id;
    }

    public static ProductId create() {
        return new ProductId(UUID.randomUUID());
    }

    public static ProductId from(UUID id) {
        return new ProductId(id);
    }

    public UUID value() {
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ProductId && id.equals(((ProductId) obj).id);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}