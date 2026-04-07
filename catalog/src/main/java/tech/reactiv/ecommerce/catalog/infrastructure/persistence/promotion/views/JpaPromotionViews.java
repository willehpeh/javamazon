package tech.reactiv.ecommerce.catalog.infrastructure.persistence.promotion.views;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaPromotionViews extends JpaRepository<PromotionViewEntity, UUID> {
}
