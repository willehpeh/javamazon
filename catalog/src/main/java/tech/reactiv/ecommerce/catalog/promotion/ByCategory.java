package tech.reactiv.ecommerce.catalog.promotion;

import tech.reactiv.ecommerce.catalog.category.CategoryId;

public record ByCategory(CategoryId categoryId) implements PromotionTarget { }
