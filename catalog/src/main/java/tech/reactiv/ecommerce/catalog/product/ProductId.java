package tech.reactiv.ecommerce.catalog.product;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;
import java.util.UUID;

public record ProductId(@JsonValue UUID value) {
    public ProductId {
        Objects.requireNonNull(value, "ID cannot be null");
    }

    public static ProductId create() {
        return new ProductId(UUID.randomUUID());
    }
}
