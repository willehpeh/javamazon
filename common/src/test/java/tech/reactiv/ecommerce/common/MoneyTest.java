package tech.reactiv.ecommerce.common;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MoneyTest {
    @ParameterizedTest
    @ValueSource(strings = {"1", "1.0", "1.000", "1.0000"})
    void shouldThrowIfScaleIsNotTwo(String value) {
        assertThatThrownBy(() -> new Money(new BigDecimal(value))).isInstanceOf(IllegalArgumentException.class);
    }
}
