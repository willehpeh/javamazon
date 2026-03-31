package tech.reactiv.ecommerce.catalog.category;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CategoryNameTest {
    @Test
    void shouldCreateCategoryName() {
        var name = new CategoryName("Toys");
        assertThat(name.value()).isEqualTo("Toys");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void shouldThrowForBlankName(String name) {
        assertThatThrownBy(() -> new CategoryName(name))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
