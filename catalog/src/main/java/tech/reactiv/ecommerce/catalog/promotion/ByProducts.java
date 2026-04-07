package tech.reactiv.ecommerce.catalog.promotion;

import tech.reactiv.ecommerce.catalog.category.CategoryId;
import tech.reactiv.ecommerce.catalog.product.ProductId;

import java.util.Set;

public record ByProducts(Set<ProductId> productIds) implements PromotionTarget {
    @Override
    public boolean appliesTo(ProductId productId, CategoryId categoryId) {
        return productIds.contains(productId);
    }
}
