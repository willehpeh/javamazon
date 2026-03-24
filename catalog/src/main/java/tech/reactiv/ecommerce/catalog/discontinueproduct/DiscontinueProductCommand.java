package tech.reactiv.ecommerce.catalog.discontinueproduct;

import tech.reactiv.ecommerce.catalog.product.ProductId;

public record DiscontinueProductCommand(ProductId productId) {
}
