package tech.reactiv.ecommerce.catalog.product;

public class ProductDescription {

    private final String description;

    public ProductDescription(String description) {
        this.description = description;
    }

    public String value() {
        return this.description;
    }
}
