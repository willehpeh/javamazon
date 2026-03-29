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
        var price = new ProductPrice(new Money(new BigDecimal(100)));
        assertThat(price.value().compareTo(new BigDecimal(100))).isEqualTo(0);
    }
    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void shouldNotAllowInvalidPrice(int price) {
        assertThatThrownBy(() -> new ProductPrice(new Money(new BigDecimal(price))))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
