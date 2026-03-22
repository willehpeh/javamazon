package tech.reactiv.ecommerce.catalog.product;

public class Product {

    private final ProductId id;
    private final ProductName name;
    private final ProductDescription description;
    private final ProductPrice price;
    private final ProductCategory category;

    public Product(ProductId id, ProductName name, ProductDescription description, ProductPrice price, ProductCategory category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public ProductId id() {
        return id;
    }

    public record State(String name, String description, int priceInCents, String category) {}

    public State state() {
        return new State(name.value(), description.value(), price.value(), category.value());
    }
}
