package tech.reactiv.ecommerce.catalog.schedulepromotion;

import org.junit.jupiter.api.Test;
import tech.reactiv.ecommerce.catalog.promotion.InMemoryPromotions;

public class SchedulePromotionTest {

    InMemoryPromotions promotions = new InMemoryPromotions();
    SchedulePromotionHandler handler = new SchedulePromotionHandler(promotions);

    @Test
    void shouldSchedulePromotion() {
        var command = new SchedulePromotionCommand();
    }
}
