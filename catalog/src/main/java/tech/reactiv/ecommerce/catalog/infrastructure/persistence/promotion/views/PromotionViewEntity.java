package tech.reactiv.ecommerce.catalog.infrastructure.persistence.promotion.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import tech.reactiv.ecommerce.catalog.promotion.PromotionDiscountPercent;
import tech.reactiv.ecommerce.catalog.promotion.PromotionTarget;
import tech.reactiv.ecommerce.catalog.promotion.views.PromotionView;
import tech.reactiv.ecommerce.common.DateRange;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Immutable
@Table(name = "promotion")
class PromotionViewEntity {

    @Id
    private UUID id;

    @Column(name = "discount_percentage", nullable = false)
    private int discountPercentage;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "target_data", columnDefinition = "jsonb")
    private PromotionTarget targetData;

    protected PromotionViewEntity() {
    }

    PromotionView toPromotionView() {
        return new PromotionView(
                new PromotionDiscountPercent(discountPercentage),
                new DateRange(startDate, endDate),
                targetData
        );
    }
}
