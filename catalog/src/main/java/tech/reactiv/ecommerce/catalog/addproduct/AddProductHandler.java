package tech.reactiv.ecommerce.catalog.addproduct;

import org.springframework.stereotype.Component;
import tech.reactiv.ecommerce.catalog.category.CategoryId;
import tech.reactiv.ecommerce.catalog.product.*;
import tech.reactiv.ecommerce.common.Money;
import tech.reactiv.ecommerce.shared.mediator.CommandHandler;

@Component
public class AddProductHandler implements CommandHandler<AddProductCommand> {

    private final Products products;

    public AddProductHandler(Products products) {
        this.products = products;
    }

    public void handle(AddProductCommand command) {
        var id = new ProductId(command.productId());
        if (products.exists(id)) {
            throw new ProductAlreadyExistsException(id);
        }
        var name = new ProductName(command.productName());
        var description = new ProductDescription(command.description());
        var price = new ProductPrice(new Money(command.price()));
        var categoryId = new CategoryId(command.categoryId());
        var product = new Product(id, name, description, price, categoryId);
        products.add(product);
    }
}
