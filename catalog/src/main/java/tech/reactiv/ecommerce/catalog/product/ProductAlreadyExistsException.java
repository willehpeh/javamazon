package tech.reactiv.ecommerce.catalog.product;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(ProductId id) {
        super("Product with id " + id + " already exists");
    }
}
