package tech.reactiv.ecommerce.catalog.category;

import tech.reactiv.ecommerce.catalog.definecategory.Categories;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryCategories implements Categories {
    public final Map<UUID, Category> list = new HashMap<>();

    @Override
    public void define(Category category) {
        list.put(category.id().value(), category);
    }
}
