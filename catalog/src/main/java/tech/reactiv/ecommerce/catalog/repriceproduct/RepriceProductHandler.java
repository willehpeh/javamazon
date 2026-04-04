package tech.reactiv.ecommerce.catalog.repriceproduct;

import org.springframework.stereotype.Component;
import tech.reactiv.ecommerce.catalog.product.*;
import tech.reactiv.ecommerce.common.Money;
import tech.reactiv.ecommerce.shared.mediator.CommandHandler;

@Component
public class RepriceProductHandler implements CommandHandler<RepriceProductCommand> {

    private final Products products;

    public RepriceProductHandler(Products products) {
        this.products = products;
    }

    public void handle(RepriceProductCommand repriceCommand) {
        var id = new ProductId(repriceCommand.productId());
        var newPrice = new ProductPrice(new Money(repriceCommand.newPrice()));

        Product product = products.productWithId(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.reprice(newPrice);

        products.modify(product);
    }
}
