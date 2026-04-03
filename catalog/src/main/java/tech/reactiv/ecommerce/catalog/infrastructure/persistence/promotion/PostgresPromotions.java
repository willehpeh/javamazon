package tech.reactiv.ecommerce.catalog.infrastructure.persistence.promotion;

import org.springframework.stereotype.Repository;
import tech.reactiv.ecommerce.catalog.promotion.Promotion;
import tech.reactiv.ecommerce.catalog.promotion.Promotions;

@Repository
class PostgresPromotions implements Promotions {

    private final JpaPromotions jpaPromotions;

    public PostgresPromotions(JpaPromotions jpaPromotions) {
        this.jpaPromotions = jpaPromotions;
    }

    @Override
    public void schedule(Promotion promotion) {
        jpaPromotions.save(PromotionEntity.fromPromotionState(promotion.state()));
    }
}
