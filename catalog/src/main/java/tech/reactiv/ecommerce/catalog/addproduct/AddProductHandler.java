package tech.reactiv.ecommerce.catalog.addproduct;

import io.opentelemetry.api.trace.Span;
import org.springframework.stereotype.Component;
import tech.reactiv.ecommerce.catalog.category.CategoryId;
import tech.reactiv.ecommerce.catalog.product.*;
import tech.reactiv.ecommerce.shared.mediator.CommandHandler;

@Component
public class AddProductHandler implements CommandHandler<AddProductCommand> {

    private final Products products;

    public AddProductHandler(Products products) {
        this.products = products;
    }

    public void handle(AddProductCommand command) {
        var span = Span.current();
        var id = new ProductId(command.productId());
        span.setAttribute("product.id", id.value().toString());
        if (products.contains(id)) {
            throw new ProductAlreadyExistsException(id);
        }
        var name = new ProductName(command.productName());
        var description = new ProductDescription(command.description());
        var price = new ProductPrice(command.price());
        var categoryId = new CategoryId(command.categoryId());
        span.setAttribute("product.name", name.value());
        span.setAttribute("product.category_id", categoryId.value().toString());
        var product = new Product(id, name, description, price, categoryId);
        products.add(product);
    }
}
