package tech.reactiv.ecommerce.catalog.promotion;

import java.time.LocalDate;
import java.util.UUID;

public class Promotion {

    private final PromotionId promotionId;
    private final PromotionDescription description;
    private final PromotionDiscountPercent discountPercent;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final PromotionTarget target;

    public Promotion(PromotionId promotionId, PromotionDescription description, PromotionDiscountPercent discountPercent, LocalDate startDate, LocalDate endDate, PromotionTarget target) {
        this.promotionId = promotionId;
        this.description = description;
        this.discountPercent = discountPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.target = target;
    }

    public PromotionId id() {
        return promotionId;
    }

    public record State(UUID id, String description, int discountPercent, LocalDate startDate, LocalDate endDate, PromotionTarget target) {
    }

    public State state() {
        return new State(promotionId.value(), description.value(), discountPercent.value(), startDate, endDate, target);
    }
}
