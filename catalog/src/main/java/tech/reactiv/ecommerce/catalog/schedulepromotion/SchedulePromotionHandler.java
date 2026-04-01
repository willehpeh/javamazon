package tech.reactiv.ecommerce.catalog.schedulepromotion;

import org.springframework.stereotype.Component;
import tech.reactiv.ecommerce.catalog.promotion.Promotion;
import tech.reactiv.ecommerce.catalog.promotion.Promotions;
import tech.reactiv.ecommerce.shared.mediator.CommandHandler;

@Component
public class SchedulePromotionHandler implements CommandHandler<SchedulePromotionCommand> {

    private final Promotions promotions;

    public SchedulePromotionHandler(Promotions promotions) {
        this.promotions = promotions;
    }

    public void handle(SchedulePromotionCommand command) {
        var promotion = new Promotion(command.promotionId(), command.description(), command.discountPercent(), command.startDate(), command.endDate(), command.target());
        promotions.schedule(promotion);
    }
}
