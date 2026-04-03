package tech.reactiv.ecommerce.catalog.discontinueproduct;

import org.springframework.stereotype.Component;
import tech.reactiv.ecommerce.catalog.product.Product;
import tech.reactiv.ecommerce.catalog.product.ProductId;
import tech.reactiv.ecommerce.catalog.product.ProductNotFoundException;
import tech.reactiv.ecommerce.catalog.product.Products;
import tech.reactiv.ecommerce.shared.mediator.CommandHandler;

@Component
public class DiscontinueProductHandler implements CommandHandler<DiscontinueProductCommand> {

    private final Products repository;

    public DiscontinueProductHandler(Products repository) {
        this.repository = repository;
    }

    public void handle(DiscontinueProductCommand command) {
        var productId = new ProductId(command.productId());
        Product product = repository.productWithId(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        product.discontinue();
        repository.addOrUpdate(product);
    }
}
