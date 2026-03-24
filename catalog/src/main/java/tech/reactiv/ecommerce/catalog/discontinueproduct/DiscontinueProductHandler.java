package tech.reactiv.ecommerce.catalog.discontinueproduct;

import tech.reactiv.ecommerce.catalog.product.Product;
import tech.reactiv.ecommerce.catalog.product.ProductNotFoundException;
import tech.reactiv.ecommerce.catalog.product.Products;

public class DiscontinueProductHandler {

    private final Products repository;

    public DiscontinueProductHandler(Products repository) {
        this.repository = repository;
    }

    public void handle(DiscontinueProductCommand command) {
        var productId = command.productId();
        Product product = repository.productWithId(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        product.discontinue();
        repository.addOrUpdate(product);
    }
}
