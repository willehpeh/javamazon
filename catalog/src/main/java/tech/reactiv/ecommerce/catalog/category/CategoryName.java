package tech.reactiv.ecommerce.catalog.category;

public record CategoryName(String value) {
    public CategoryName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Category name must not be blank");
        }
    }
}
