package tech.reactiv.ecommerce.catalog.fixtures;

import tech.reactiv.ecommerce.catalog.promotion.PromotionTarget;

import java.time.LocalDate;
import java.util.UUID;

public interface CatalogFixtures {
    UUID createCategory(String name);
    UUID schedulePromotion(String description, int discountPercent, LocalDate startDate, LocalDate endDate, PromotionTarget target);
}
