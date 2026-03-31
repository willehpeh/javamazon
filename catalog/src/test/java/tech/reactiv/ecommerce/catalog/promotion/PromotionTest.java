package tech.reactiv.ecommerce.catalog.promotion;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PromotionTest {
    @Test
    void shouldCreatePromotion() {
        PromotionId promotionId = new PromotionId(UUID.randomUUID());
        PromotionDescription description = new PromotionDescription("Summer Sale");
        PromotionDiscountPercent discountPercent = new PromotionDiscountPercent(20);
        LocalDate startDate = LocalDate.of(2023, 6, 1);
        LocalDate endDate = LocalDate.of(2023, 8, 31);
        PromotionTarget target = new AllProducts();

        Promotion promotion = new Promotion(promotionId, description, discountPercent, startDate, endDate, target);

        assertThat(promotion.state().id()).isEqualTo(promotionId.value());
        assertThat(promotion.state().description()).isEqualTo(description.value());
        assertThat(promotion.state().discountPercent()).isEqualTo(discountPercent.value());
        assertThat(promotion.state().startDate()).isEqualTo(startDate);
        assertThat(promotion.state().endDate()).isEqualTo(endDate);
        assertThat(promotion.state().target()).isEqualTo(target);
    }

    @Test
    void shouldNotCreatePromotionIfEndDateIsBeforeStartDate() {
        PromotionId promotionId = new PromotionId(UUID.randomUUID());
        PromotionDescription description = new PromotionDescription("Summer Sale");
        PromotionDiscountPercent discountPercent = new PromotionDiscountPercent(20);
        LocalDate startDate = LocalDate.of(2023, 8, 31);
        LocalDate invalidEndDate = LocalDate.of(2023, 6, 1);
        PromotionTarget target = new AllProducts();

        assertThatThrownBy(() -> new Promotion(promotionId, description, discountPercent, startDate, invalidEndDate, target))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
