package tech.reactiv.ecommerce.catalog.promotion;

import java.time.LocalDate;
import java.util.UUID;

public class Promotion {

    private final PromotionId id;
    private final PromotionDescription description;
    private final PromotionDiscountPercent discountPercent;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final PromotionTarget target;

    public Promotion(PromotionId id, PromotionDescription description, PromotionDiscountPercent discountPercent, LocalDate startDate, LocalDate endDate, PromotionTarget target) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        this.id = id;
        this.description = description;
        this.discountPercent = discountPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.target = target;
    }

    public PromotionId id() {
        return id;
    }

    public record State(UUID id, String description, int discountPercent, LocalDate startDate, LocalDate endDate, PromotionTarget target) {
    }

    public State state() {
        return new State(id.value(), description.value(), discountPercent.value(), startDate, endDate, target);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Promotion that)) {
            return false;
        }
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
