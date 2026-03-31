package tech.reactiv.ecommerce.catalog.category;

import java.util.Objects;
import java.util.UUID;

public record CategoryId(UUID value) {
    public CategoryId {
        Objects.requireNonNull(value, "Category ID cannot be null");
    }

    public static CategoryId create() {
        return new CategoryId(UUID.randomUUID());
    }
}
