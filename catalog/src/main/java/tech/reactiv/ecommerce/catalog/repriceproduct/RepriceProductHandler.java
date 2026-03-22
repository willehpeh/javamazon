package tech.reactiv.ecommerce.catalog.repriceproduct;

import tech.reactiv.ecommerce.catalog.product.*;

public class RepriceProductHandler {

    private final ProductRepository repository;

    public RepriceProductHandler(ProductRepository repository) {
        this.repository = repository;
    }

    public void handle(RepriceProductCommand repriceCommand) {
        var id = ProductId.from(repriceCommand.productId());
        var newPrice = new ProductPrice(repriceCommand.newPriceInCents());

        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.reprice(newPrice);

        repository.save(product);
    }
}
