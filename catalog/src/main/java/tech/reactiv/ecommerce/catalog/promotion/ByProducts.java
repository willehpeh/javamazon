package tech.reactiv.ecommerce.catalog.promotion;

import tech.reactiv.ecommerce.catalog.product.ProductId;

import java.util.Set;

public record ByProducts(Set<ProductId> productIds) implements PromotionTarget {
}
