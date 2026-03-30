package tech.reactiv.ecommerce.catalog.schedulepromotion;

import tech.reactiv.ecommerce.catalog.promotion.Promotion;
import tech.reactiv.ecommerce.catalog.promotion.Promotions;

public class SchedulePromotionHandler {

    private final Promotions promotions;

    public SchedulePromotionHandler(Promotions promotions) {
        this.promotions = promotions;
    }

    public void handle(SchedulePromotionCommand command) {
        var promotion = new Promotion(command.promotionId(), command.description(), command.discountPercent(), command.startDate(), command.endDate(), command.target());
        promotions.schedule(promotion);
    }
}
