package tech.reactiv.ecommerce.catalog.product;

import java.util.Objects;
import java.util.UUID;

public record ProductId(UUID value) {
    public ProductId {
        Objects.requireNonNull(value, "ID cannot be null");
    }

    public static ProductId create() {
        return new ProductId(UUID.randomUUID());
    }
}
