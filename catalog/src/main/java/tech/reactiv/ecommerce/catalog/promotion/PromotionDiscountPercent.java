package tech.reactiv.ecommerce.catalog.promotion;

public record PromotionDiscountPercent(int value) {
    public PromotionDiscountPercent {
        if (value <= 0 || value > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100");
        }
    }
}
