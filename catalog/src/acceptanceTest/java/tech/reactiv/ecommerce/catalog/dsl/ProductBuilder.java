package tech.reactiv.ecommerce.catalog.dsl;

public class ProductBuilder {

    private String name = "Default Product";
    private String description = "A default product description";
    private String price = "10.00";
    private String category;

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

    public ProductBuilder inCategory(String category) {
        this.category = category;
        return this;
    }

    String name() { return name; }
    String description() { return description; }
    String price() { return price; }
    String category() { return category; }
}
