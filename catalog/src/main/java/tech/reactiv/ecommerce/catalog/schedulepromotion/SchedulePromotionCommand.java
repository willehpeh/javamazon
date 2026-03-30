package tech.reactiv.ecommerce.catalog.schedulepromotion;

import tech.reactiv.ecommerce.catalog.promotion.PromotionDescription;
import tech.reactiv.ecommerce.catalog.promotion.PromotionDiscountPercent;
import tech.reactiv.ecommerce.catalog.promotion.PromotionId;
import tech.reactiv.ecommerce.catalog.promotion.PromotionTarget;

import java.time.LocalDate;

public record SchedulePromotionCommand(PromotionId promotionId,
                                       PromotionDescription description,
                                       PromotionDiscountPercent discountPercent,
                                       LocalDate startDate,
                                       LocalDate endDate,
                                       PromotionTarget target) {
}
