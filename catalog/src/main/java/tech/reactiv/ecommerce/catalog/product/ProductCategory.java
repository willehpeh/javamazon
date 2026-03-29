package tech.reactiv.ecommerce.catalog.product;

public record ProductCategory(String value) {
    public ProductCategory {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Category must not be blank");
        }
    }
}
