package tech.reactiv.ecommerce.catalog.product;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(ProductId id) {
        super("Product with id " + id + " not found");
    }
}
