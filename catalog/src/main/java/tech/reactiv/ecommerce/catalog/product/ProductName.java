package tech.reactiv.ecommerce.catalog.product;

public class ProductName {

    private final String name;

    public ProductName(String name) {
        this.name = name;
    }

    public String value() {
        return this.name;
    }
}
