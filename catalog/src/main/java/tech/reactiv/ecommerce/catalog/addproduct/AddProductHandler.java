package tech.reactiv.ecommerce.catalog.addproduct;

import tech.reactiv.ecommerce.catalog.category.CategoryId;
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
        var categoryId = new CategoryId(command.categoryId());
        var id = new ProductId(command.productId());
        var product = new Product(id, name, description, price, categoryId);
        repository.addOrUpdate(product);
    }
}
