package tech.reactiv.ecommerce.catalog.product;

public class ProductDescription {

    private final String description;

    public ProductDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
        this.description = description;
    }

    public String value() {
        return this.description;
    }
}
