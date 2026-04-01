package tech.reactiv.ecommerce.catalog.discontinueproduct;

import tech.reactiv.ecommerce.catalog.product.ProductId;
import tech.reactiv.ecommerce.shared.mediator.Command;

public record DiscontinueProductCommand(ProductId productId) implements Command {
}
