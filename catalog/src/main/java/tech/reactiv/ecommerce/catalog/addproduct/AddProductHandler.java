package tech.reactiv.ecommerce.catalog.addproduct;

import tech.reactiv.ecommerce.catalog.product.*;
import tech.reactiv.ecommerce.common.Money;

public class AddProductHandler {

    private final Products repository;

    public AddProductHandler(Products repository) {
        this.repository = repository;
    }

    public void handle(AddProductCommand command) {
        var name = new ProductName(command.productName());
        var description = new ProductDescription(command.description());
        var price = new ProductPrice(new Money(command.price()));
        var category = new ProductCategory(command.category());
        var id = new ProductId(command.productId());
        var product = new Product(id, name, description, price, category);
        repository.addOrUpdate(product);
    }
}
