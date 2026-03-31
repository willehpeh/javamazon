package tech.reactiv.ecommerce.catalog.promotion;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PromotionDescriptionTest {
    @Test
    void shouldCreatePromotionDescription() {
        var description = new PromotionDescription("10% off");
        assertThat(description.value()).isEqualTo("10% off");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    void shouldNotCreateDescriptionForNullOrEmptyString(String invalidValue) {
        assertThatThrownBy(() -> new PromotionDescription(invalidValue))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
