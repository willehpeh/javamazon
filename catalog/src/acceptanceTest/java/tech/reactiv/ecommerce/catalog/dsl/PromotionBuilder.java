package tech.reactiv.ecommerce.catalog.dsl;

import tech.reactiv.ecommerce.catalog.promotion.AllProducts;
import tech.reactiv.ecommerce.catalog.promotion.PromotionTarget;

import java.time.LocalDate;

public class PromotionBuilder {

    private String description = "Default Promotion";
    private int discountPercent = 10;
    private LocalDate startDate = LocalDate.now().minusDays(1);
    private LocalDate endDate = LocalDate.now().plusDays(1);
    private PromotionTarget target = new AllProducts();

    static PromotionBuilder withDefaults() {
        return new PromotionBuilder();
    }

    public PromotionBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public PromotionBuilder withDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
        return this;
    }

    public PromotionBuilder withStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public PromotionBuilder withEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public PromotionBuilder withTarget(PromotionTarget target) {
        this.target = target;
        return this;
    }

    String description() { return description; }
    int discountPercent() { return discountPercent; }
    LocalDate startDate() { return startDate; }
    LocalDate endDate() { return endDate; }
    PromotionTarget target() { return target; }
}
