package tech.reactiv.ecommerce.catalog.promotion;

public record PromotionDescription(String value) {
    public PromotionDescription {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Promotion description cannot be null");
        }
    }
}
