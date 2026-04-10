package tech.reactiv.ecommerce.catalog.infrastructure.persistence.promotion;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.ColumnTransformer;
import tech.reactiv.ecommerce.catalog.promotion.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "promotion")
class PromotionEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String description;

    @Column(name = "discount_percentage", nullable = false)
    private int discountPercentage;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Convert(converter = PromotionTargetConverter.class)
    @Column(name = "target_data", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private PromotionTarget targetData;

    protected PromotionEntity() {
    }

    static PromotionEntity fromPromotionState(Promotion.State state) {
        var entity = new PromotionEntity();
        entity.id = state.id();
        entity.description = state.description();
        entity.discountPercentage = state.discountPercent();
        entity.startDate = state.startDate();
        entity.endDate = state.endDate();
        entity.targetData = state.target();
        return entity;
    }

    Promotion toPromotion() {
        return new Promotion(
                new PromotionId(id),
                new PromotionDescription(description),
                new PromotionDiscountPercent(discountPercentage),
                startDate,
                endDate,
                targetData
        );
    }
}
