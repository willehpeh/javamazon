package tech.reactiv.ecommerce.catalog.addtocatalog;

import tech.reactiv.ecommerce.catalog.product.*;
import tech.reactiv.ecommerce.common.Money;

public class AddToCatalogHandler {

    private final Products repository;

    public AddToCatalogHandler(Products repository) {
        this.repository = repository;
    }

    public ProductId handle(AddToCatalogCommand command) {
        var id = ProductId.create();
        var name = new ProductName(command.productName());
        var description = new ProductDescription(command.description());
        var price = new ProductPrice(new Money(command.price()));
        var category = new ProductCategory(command.category());
        var product = new Product(id, name, description, price, category);
        repository.addOrUpdate(product);
        return id;
    }
}
