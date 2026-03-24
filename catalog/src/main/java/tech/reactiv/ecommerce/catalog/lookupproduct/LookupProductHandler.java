package tech.reactiv.ecommerce.catalog.lookupproduct;

import tech.reactiv.ecommerce.catalog.product.Product;
import tech.reactiv.ecommerce.catalog.product.ProductId;
import tech.reactiv.ecommerce.catalog.product.Products;

import java.util.Optional;

public class LookupProductHandler {

    private final Products repository;

    public LookupProductHandler(Products repository) {
        this.repository = repository;
    }

    public Optional<Product> handle(ProductId productId) {
        return repository.productWithId(productId);
    }
}
