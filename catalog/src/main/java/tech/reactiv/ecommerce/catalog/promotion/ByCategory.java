package tech.reactiv.ecommerce.catalog.promotion;

import tech.reactiv.ecommerce.catalog.category.CategoryId;
import tech.reactiv.ecommerce.catalog.product.ProductId;

public record ByCategory(CategoryId categoryId) implements PromotionTarget {
    @Override
    public boolean appliesTo(ProductId productId, CategoryId categoryId) {
        return this.categoryId.equals(categoryId);
    }
}
