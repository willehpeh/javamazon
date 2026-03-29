package tech.reactiv.ecommerce.catalog.product;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductIdTest {
    @Test
    void shouldCreateProductId() {
        var id = ProductId.create();
        assertThat(id.value()).isNotNull();
    }

    @Test
    void shouldCreateProductIdFromUUID() {
        var uuid = UUID.randomUUID();
        var id = ProductId.from(uuid);
        assertThat(id.value()).isEqualTo(uuid);
    }

    @Test
    void shouldThrowForEmptyId() {
        assertThatThrownBy(() -> ProductId.from(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
