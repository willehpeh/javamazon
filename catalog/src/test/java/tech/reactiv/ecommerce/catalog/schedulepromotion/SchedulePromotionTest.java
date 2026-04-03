package tech.reactiv.ecommerce.catalog.schedulepromotion;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tech.reactiv.ecommerce.catalog.promotion.*;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class SchedulePromotionTest {

    InMemoryPromotions promotions = new InMemoryPromotions();
    SchedulePromotionHandler handler = new SchedulePromotionHandler(promotions);

    public static Stream<String> promotionTargets() {
        return Stream.of("ALL_PRODUCTS");
    }

    @ParameterizedTest
    @MethodSource("promotionTargets")
    void shouldSchedulePromotionsWithTarget(String target) {
        var id = UUID.randomUUID();
        var description = "10% off on all items";
        var discountPercent = 10;
        var startDate = LocalDate.now().plusDays(1);
        var endDate = startDate.plusDays(7);
        var command = new SchedulePromotionCommand(id, description, discountPercent, startDate, endDate, target);

        handler.handle(command);

        var newPromotion = promotions.list.get(new PromotionId(id));
        assertThat(newPromotion).isNotNull();
        assertThat(newPromotion.state().id()).isEqualTo(id);
        assertThat(newPromotion.state().description()).isEqualTo(description);
        assertThat(newPromotion.state().discountPercent()).isEqualTo(discountPercent);
        assertThat(newPromotion.state().startDate()).isEqualTo(startDate);
        assertThat(newPromotion.state().endDate()).isEqualTo(endDate);
    }
}
