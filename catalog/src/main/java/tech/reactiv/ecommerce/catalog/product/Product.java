package tech.reactiv.ecommerce.catalog.product;

public class Product {

    private final ProductId id;
    private final ProductName name;
    private final ProductDescription description;
    private final ProductPrice price;

    public Product(ProductId id, ProductName name, ProductDescription description, ProductPrice price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public ProductId id() {
        return id;
    }

    public record State(String name, String description, int priceInCents) {}

    public State state() {
        return new State(name.value(), description.value(), price.value());
    }
}
