package tech.reactiv.ecommerce.catalog.product;

public class ProductCategory {

    private final String category;

    public ProductCategory(String category) {
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("Category must not be blank");
        }
        this.category = category;
    }

    public String value() {
        return this.category;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ProductCategory other && category.equals(other.category);
    }

    @Override
    public int hashCode() {
        return category.hashCode();
    }

    @Override
    public String toString() {
        return category;
    }
}
