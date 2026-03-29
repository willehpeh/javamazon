package tech.reactiv.ecommerce.catalog.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tech.reactiv.ecommerce.common.Money;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductPriceTest {
    @Test
    void shouldCreateProductPrice() {
        var price = new ProductPrice(new Money("100.00"));
        assertThat(price.value()).isEqualByComparingTo(new BigDecimal("100.00"));
    }

    @Test
    void shouldCreateProductPriceFromString() {
        var price = new ProductPrice("100.00");
        assertThat(price.value()).isEqualByComparingTo(new BigDecimal("100.00"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0.00", "-1.00"})
    void shouldNotAllowInvalidPrice(String price) {
        assertThatThrownBy(() -> new ProductPrice(new Money(price)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
