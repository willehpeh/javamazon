package tech.reactiv.ecommerce.catalog.promotion;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PromotionDiscountPercentTest {
    @Test
    void shouldCreatePromotionDiscountPercent() {
        var discountPercent = new PromotionDiscountPercent(10);
        assertThat(discountPercent.value()).isEqualTo(10);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 101})
    void shouldThrowForInvalidDiscountPercentages(int invalidValue) {
        assertThatThrownBy(() -> new PromotionDiscountPercent(invalidValue))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
