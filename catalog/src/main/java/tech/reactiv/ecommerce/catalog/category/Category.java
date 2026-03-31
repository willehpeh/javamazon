package tech.reactiv.ecommerce.catalog.category;

import java.util.UUID;

public class Category {

    private final CategoryId id;
    private final CategoryName name;

    public Category(CategoryId id, CategoryName name) {
        this.id = id;
        this.name = name;
    }

    public CategoryId id() {
        return id;
    }

    public record State(UUID id, String name) {
    }

    public State state() {
        return new State(id.value(), name.value());
    }
}
