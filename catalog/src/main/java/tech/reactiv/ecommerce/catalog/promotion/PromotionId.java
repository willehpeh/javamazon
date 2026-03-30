package tech.reactiv.ecommerce.catalog.promotion;

import java.util.UUID;

public record PromotionId(UUID value) {
    public static PromotionId create() {
        return new PromotionId(UUID.randomUUID());
    }
}
