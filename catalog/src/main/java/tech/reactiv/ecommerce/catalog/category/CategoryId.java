package tech.reactiv.ecommerce.catalog.category;

import java.util.UUID;

public record CategoryId(UUID value) {
    public CategoryId {
        if (value == null) {
            throw new IllegalArgumentException("Category ID cannot be null");
        }
    }

    public static CategoryId create() {
        return new CategoryId(UUID.randomUUID());
    }
}
