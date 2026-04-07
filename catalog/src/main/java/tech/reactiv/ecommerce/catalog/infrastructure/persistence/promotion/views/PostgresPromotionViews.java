package tech.reactiv.ecommerce.catalog.infrastructure.persistence.promotion.views;

import org.springframework.stereotype.Repository;
import tech.reactiv.ecommerce.catalog.promotion.views.PromotionView;
import tech.reactiv.ecommerce.catalog.promotion.views.PromotionViews;

import java.util.List;

@Repository
public class PostgresPromotionViews implements PromotionViews {

    private final JpaPromotionViews jpaPromotionViews;

    public PostgresPromotionViews(JpaPromotionViews jpaPromotionViews) {
        this.jpaPromotionViews = jpaPromotionViews;
    }

    @Override
    public List<PromotionView> all() {
        return jpaPromotionViews.findAll().stream()
                .map(PromotionViewEntity::toPromotionView)
                .toList();
    }
}
