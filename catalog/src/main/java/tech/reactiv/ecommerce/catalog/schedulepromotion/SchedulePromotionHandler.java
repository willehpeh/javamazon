package tech.reactiv.ecommerce.catalog.schedulepromotion;

import org.springframework.stereotype.Component;
import tech.reactiv.ecommerce.catalog.promotion.*;
import tech.reactiv.ecommerce.shared.mediator.CommandHandler;

@Component
public class SchedulePromotionHandler implements CommandHandler<SchedulePromotionCommand> {

    private final Promotions promotions;

    public SchedulePromotionHandler(Promotions promotions) {
        this.promotions = promotions;
    }

    public void handle(SchedulePromotionCommand command) {
        var promotion = new Promotion(
                new PromotionId(command.promotionId()),
                new PromotionDescription(command.description()),
                new PromotionDiscountPercent(command.discountPercent()),
                command.startDate(),
                command.endDate(),
                toTarget(command.target())
        );
        promotions.schedule(promotion);
    }

    private PromotionTarget toTarget(String target) {
        return switch (target) {
            case "ALL_PRODUCTS" -> new AllProducts();
            default -> throw new IllegalArgumentException("Unknown promotion target: " + target);
        };
    }
}
