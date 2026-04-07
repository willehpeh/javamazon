package tech.reactiv.ecommerce.catalog.promotion;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import tech.reactiv.ecommerce.catalog.category.CategoryId;
import tech.reactiv.ecommerce.catalog.product.ProductId;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AllProducts.class, name = "ALL_PRODUCTS"),
        @JsonSubTypes.Type(value = ByCategory.class, name = "BY_CATEGORY"),
        @JsonSubTypes.Type(value = ByProducts.class, name = "BY_PRODUCTS")
})
public sealed interface PromotionTarget permits AllProducts, ByCategory, ByProducts {
    boolean appliesTo(ProductId productId, CategoryId categoryId);
}
