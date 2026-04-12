package tech.reactiv.ecommerce.catalog.dsl;

import tech.reactiv.ecommerce.catalog.category.CategoryId;
import tech.reactiv.ecommerce.catalog.product.ProductId;
import tech.reactiv.ecommerce.catalog.promotion.AllProducts;
import tech.reactiv.ecommerce.catalog.promotion.ByCategory;
import tech.reactiv.ecommerce.catalog.promotion.ByProducts;
import tech.reactiv.ecommerce.catalog.promotion.PromotionTarget;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class PromotionBuilder {

    private String description = "Default Promotion";
    private int discountPercent = 10;
    private LocalDate startDate = LocalDate.now().minusDays(1);
    private LocalDate endDate = LocalDate.now().plusDays(1);
    private PromotionTarget target = new AllProducts();

    static PromotionBuilder withDefaults() {
        return new PromotionBuilder();
    }

    public PromotionBuilder withDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
        return this;
    }

    public PromotionBuilder targetingCategory(UUID categoryId) {
        this.target = new ByCategory(new CategoryId(categoryId));
        return this;
    }

    public PromotionBuilder targetingProduct(UUID productId) {
        this.target = new ByProducts(Set.of(new ProductId(productId)));
        return this;
    }

    public PromotionBuilder targetingAllProducts() {
        this.target = new AllProducts();
        return this;
    }

    String description() { return description; }
    int discountPercent() { return discountPercent; }
    LocalDate startDate() { return startDate; }
    LocalDate endDate() { return endDate; }
    PromotionTarget target() { return target; }
}
