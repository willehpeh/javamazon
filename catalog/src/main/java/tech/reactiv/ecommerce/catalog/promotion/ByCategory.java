package tech.reactiv.ecommerce.catalog.promotion;

import tech.reactiv.ecommerce.catalog.category.CategoryId;

record ByCategory(CategoryId categoryId) implements PromotionTarget { }
