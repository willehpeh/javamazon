package tech.reactiv.ecommerce.catalog.product;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

    private final ProductId id;
    private final ProductName name;
    private final ProductDescription description;
    private ProductPrice price;
    private final ProductCategory category;
    private boolean active;

    public Product(ProductId id, ProductName name, ProductDescription description, ProductPrice price, ProductCategory category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
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

    public record State(UUID id, String name, String description, BigDecimal price, String category, boolean active) {
    }

    public State state() {
        return new State(id.value(), name.value(), description.value(), price.value(), category.value(), active);
    }
}
