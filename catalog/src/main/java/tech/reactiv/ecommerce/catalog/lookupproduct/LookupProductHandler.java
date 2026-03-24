package tech.reactiv.ecommerce.catalog.lookupproduct;

import tech.reactiv.ecommerce.catalog.product.Product;
import tech.reactiv.ecommerce.catalog.product.ProductId;
import tech.reactiv.ecommerce.catalog.product.ProductRepository;

import java.util.Optional;

public class LookupProductHandler {

    private final ProductRepository repository;

    public LookupProductHandler(ProductRepository repository) {
        this.repository = repository;
    }

    public Optional<Product> handle(ProductId productId) {
        return repository.findById(productId);
    }
}
