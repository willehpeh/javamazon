package tech.reactiv.ecommerce.catalog.fixtures;

import tech.reactiv.ecommerce.catalog.promotion.PromotionTarget;

import java.time.LocalDate;
import java.util.UUID;

public interface CatalogFixtures {
    UUID insertCategory(String name);
    UUID insertPromotion(String description, int discountPercent, LocalDate startDate, LocalDate endDate, PromotionTarget target);
    UUID insertProduct(String name, String description, String price, UUID uuid);
}
