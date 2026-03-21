package tech.reactiv.ecommerce.catalog.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductNameTest {
    @Test
    void shouldCreateProductName() {
        var name = new ProductName("Product Name");
        assertThat(name.value()).isEqualTo("Product Name");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    void shouldNotAllowInvalidName(String name) {
        assertThatThrownBy(() -> new ProductName(name))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
