package tech.reactiv.ecommerce.common;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MoneyTest {
    @Test
    void shouldCreateMoney() {
        var money = new Money(new BigDecimal("1.00"));
        assertThat(money.value()).isEqualByComparingTo(new BigDecimal("1.00"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "1.0", "1.00"})
    void shouldCreateMoneyFromString(String value) {
        var money = new Money(value);
        assertThat(money.value()).isEqualByComparingTo(new BigDecimal("1.00"));
    }

    @Test
    void shouldCreateMoneyFromInt() {
        var money = new Money(10);
        assertThat(money.value()).isEqualByComparingTo(new BigDecimal("10.00"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1.000", "1.0000"})
    void shouldThrowIfScaleIsGreaterThanTwo(String value) {
        assertThatThrownBy(() -> new Money(new BigDecimal(value))).isInstanceOf(IllegalArgumentException.class);
    }

    @Nested
    class Operations {
        @Test
        void shouldAdd() {
            var money1 = new Money("1.00");
            var money2 = new Money("2.00");
            var result = money1.add(money2);
            var expected = new Money("3.00");

            assertThat(result).isEqualTo(expected);
        }

        @Test
        void shouldSubtract() {
            var money1 = new Money("3.00");
            var money2 = new Money("2.00");
            var result = money1.subtract(money2);
            var expected = new Money("1.00");

            assertThat(result).isEqualTo(expected);
        }

        @Test
        void shouldMultiply() {
            var money = new Money("2.00");
            var result = money.multiply(5);
            var expected = new Money("10.00");

            assertThat(result).isEqualTo(expected);
        }

        @Test
        void shouldApplyPercentage() {
            var money = new Money("100.00");
            var result = money.percent(5);
            var expected = new Money("5.00");

            assertThat(result).isEqualTo(expected);
        }

        @Test
        void shouldRoundUpPercentage() {
            var money = new Money("1.05");
            var result = money.percent(50);
            var expected = new Money("0.53");

            assertThat(result).isEqualTo(expected);
        }

        @Test
        void shouldRefuseNegativePercentage() {
            assertThatThrownBy(() -> new Money("1.00").percent(-5)).isInstanceOf(IllegalArgumentException.class);
        }
    }
}
