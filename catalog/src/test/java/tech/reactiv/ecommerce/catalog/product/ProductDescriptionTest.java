package tech.reactiv.ecommerce.catalog.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductDescriptionTest {
    @Test
    void shouldCreateProductDescription() {
        var description = new ProductDescription("Sample description");
        assertThat(description.value()).isEqualTo("Sample description");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    void shouldNotCreateEmptyOrNullProductDescription(String invalidValue) {
        assertThatThrownBy(() -> new ProductDescription(invalidValue))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
