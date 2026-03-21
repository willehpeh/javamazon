package tech.reactiv.ecommerce.catalog.product;

public class ProductName {

    private final String name;

    public ProductName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        this.name = name;
    }

    public String value() {
        return this.name;
    }
}
