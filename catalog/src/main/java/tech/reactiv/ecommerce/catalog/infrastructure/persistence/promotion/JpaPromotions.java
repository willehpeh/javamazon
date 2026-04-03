package tech.reactiv.ecommerce.catalog.infrastructure.persistence.promotion;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaPromotions extends JpaRepository<PromotionEntity, UUID> {
}
