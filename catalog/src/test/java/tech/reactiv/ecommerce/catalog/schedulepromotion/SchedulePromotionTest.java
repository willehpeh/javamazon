package tech.reactiv.ecommerce.catalog.schedulepromotion;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tech.reactiv.ecommerce.catalog.promotion.*;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class SchedulePromotionTest {

    InMemoryPromotions promotions = new InMemoryPromotions();
    SchedulePromotionHandler handler = new SchedulePromotionHandler(promotions);

    public static Stream<PromotionTarget> promotionTargets() {
        return Stream.of(new AllProducts());
    }

    @ParameterizedTest
    @MethodSource("promotionTargets")
    void shouldSchedulePromotionsWithTarget(PromotionTarget target) {
        var id = PromotionId.create();
        var description = new PromotionDescription("10% off on all items");
        var discountPercent = new PromotionDiscountPercent(10);
        var startDate = LocalDate.now().plusDays(1);
        var endDate = startDate.plusDays(7);
        var command = new SchedulePromotionCommand(id, description, discountPercent, startDate, endDate, target);

        handler.handle(command);

        var newPromotion = promotions.list.get(id);
        assertThat(newPromotion).isNotNull();
        assertThat(newPromotion.state().id()).isEqualTo(id.value());
        assertThat(newPromotion.state().description()).isEqualTo(description.value());
        assertThat(newPromotion.state().discountPercent()).isEqualTo(discountPercent.value());
        assertThat(newPromotion.state().startDate()).isEqualTo(startDate);
        assertThat(newPromotion.state().endDate()).isEqualTo(endDate);
        assertThat(newPromotion.state().target()).isEqualTo(target);
    }
}
