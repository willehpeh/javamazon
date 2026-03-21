package tech.reactiv.ecommerce.catalog.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductPriceTest {
    @Test
    void shouldCreateProductPrice() {
        var price = new ProductPrice(100);
        assertThat(price.value()).isEqualTo(100);
    }
    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void shouldNotAllowInvalidPrice(int price) {
        assertThatThrownBy(() -> new ProductPrice(price))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
