package tech.reactiv.ecommerce.catalog.schedulepromotion;

import tech.reactiv.ecommerce.catalog.promotion.PromotionTarget;
import tech.reactiv.ecommerce.shared.mediator.Command;

import java.time.LocalDate;
import java.util.UUID;

public record SchedulePromotionCommand(UUID promotionId,
                                       String description,
                                       int discountPercent,
                                       LocalDate startDate,
                                       LocalDate endDate,
                                       PromotionTarget target) implements Command {
}
