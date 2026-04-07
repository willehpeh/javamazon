package tech.reactiv.ecommerce.catalog.category;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;
import java.util.UUID;

public record CategoryId(@JsonValue UUID value) {
    public CategoryId {
        Objects.requireNonNull(value, "Category ID cannot be null");
    }

    public static CategoryId create() {
        return new CategoryId(UUID.randomUUID());
    }
}
