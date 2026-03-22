package tech.reactiv.ecommerce.catalog.product;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductIdTest {
    @Test
    void shouldThrowForEmptyId() {
        assertThatThrownBy(() -> ProductId.from(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
