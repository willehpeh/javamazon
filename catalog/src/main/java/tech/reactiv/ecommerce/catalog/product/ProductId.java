package tech.reactiv.ecommerce.catalog.product;

import java.util.UUID;

public record ProductId(UUID value) {
    public ProductId {
        if (value == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
    }

    public static ProductId create() {
        return new ProductId(UUID.randomUUID());
    }
}
