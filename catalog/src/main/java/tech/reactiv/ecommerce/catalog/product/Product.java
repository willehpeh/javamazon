package tech.reactiv.ecommerce.catalog.product;

import tech.reactiv.ecommerce.catalog.category.CategoryId;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

    private final ProductId id;
    private final ProductName name;
    private final ProductDescription description;
    private ProductPrice price;
    private final CategoryId categoryId;
    private boolean active;

    public Product(ProductId id, ProductName name, ProductDescription description, ProductPrice price, CategoryId categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.active = true;
    }

    public ProductId id() {
        return id;
    }

    public void reprice(ProductPrice newPrice) {
        this.price = newPrice;
    }

    public void discontinue() {
        this.active = false;
    }

    public record State(UUID id, String name, String description, BigDecimal price, UUID categoryId, boolean active) {
    }

    public State state() {
        return new State(id.value(), name.value(), description.value(), price.value(), categoryId.value(), active);
    }
}
