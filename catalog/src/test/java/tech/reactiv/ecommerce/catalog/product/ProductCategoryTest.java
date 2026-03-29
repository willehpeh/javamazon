package tech.reactiv.ecommerce.catalog.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductCategoryTest {
    @Test
    void shouldCreateProductCategory() {
        var category = new ProductCategory("Toys");
        assertThat(category.value()).isEqualTo("Toys");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void shouldThrowForEmptyCategory(String category) {
        assertThatThrownBy(() -> new ProductCategory(category))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
