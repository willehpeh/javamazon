package tech.reactiv.ecommerce.catalog.dsl;

import java.util.UUID;

public class ProductBuilder {

    private String name = "Default Product";
    private String description = "A default product description";
    private String price = "10.00";
    private UUID categoryId;

    static ProductBuilder withDefaults() {
        return new ProductBuilder();
    }

    public ProductBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductBuilder withPrice(String price) {
        this.price = price;
        return this;
    }

    public ProductBuilder inCategory(UUID categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    String name() { return name; }
    String description() { return description; }
    String price() { return price; }
    UUID categoryId() { return categoryId; }
}
