package tech.reactiv.ecommerce.catalog.promotion;

import tech.reactiv.ecommerce.catalog.category.CategoryId;
import tech.reactiv.ecommerce.catalog.product.ProductId;

public record AllProducts() implements PromotionTarget {
    @Override
    public boolean appliesTo(ProductId productId, CategoryId categoryId) {
        return true;
    }
}
