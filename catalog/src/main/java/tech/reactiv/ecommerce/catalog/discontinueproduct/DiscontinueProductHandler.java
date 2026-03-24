package tech.reactiv.ecommerce.catalog.discontinueproduct;

import tech.reactiv.ecommerce.catalog.product.Product;
import tech.reactiv.ecommerce.catalog.product.ProductNotFoundException;
import tech.reactiv.ecommerce.catalog.product.ProductRepository;

public class DiscontinueProductHandler {

    private final ProductRepository repository;

    public DiscontinueProductHandler(ProductRepository repository) {
        this.repository = repository;
    }

    public void handle(DiscontinueProductCommand command) {
        var productId = command.productId();
        Product product = repository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        product.discontinue();
        repository.save(product);
    }
}
