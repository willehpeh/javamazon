package tech.reactiv.ecommerce.catalog.addtocatalog;

import tech.reactiv.ecommerce.catalog.product.*;

public class AddToCatalogHandler {

    private final ProductRepository repository;

    public AddToCatalogHandler(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductId handle(AddToCatalogCommand command) {
        var id = ProductId.create();
        var name = new ProductName(command.productName());
        var description = new ProductDescription(command.description());
        var price = new ProductPrice(command.priceInCents());
        var product = new Product(id, name, description, price);
        repository.add(product);
        return id;
    }
}
