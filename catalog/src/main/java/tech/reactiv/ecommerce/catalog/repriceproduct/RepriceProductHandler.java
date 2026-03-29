package tech.reactiv.ecommerce.catalog.repriceproduct;

import tech.reactiv.ecommerce.catalog.product.*;
import tech.reactiv.ecommerce.common.Money;

public class RepriceProductHandler {

    private final Products repository;

    public RepriceProductHandler(Products repository) {
        this.repository = repository;
    }

    public void handle(RepriceProductCommand repriceCommand) {
        var id = ProductId.from(repriceCommand.productId());
        var newPrice = new ProductPrice(new Money(repriceCommand.newPriceInCents()));

        Product product = repository.productWithId(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.reprice(newPrice);

        repository.addOrUpdate(product);
    }
}
