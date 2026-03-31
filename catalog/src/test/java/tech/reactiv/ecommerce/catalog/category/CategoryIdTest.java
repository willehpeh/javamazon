package tech.reactiv.ecommerce.catalog.category;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CategoryIdTest {
    @Test
    void shouldCreateCategoryId() {
        var id = CategoryId.create();
        assertThat(id.value()).isNotNull();
    }

    @Test
    void shouldCreateCategoryIdFromUUID() {
        var uuid = UUID.randomUUID();
        var id = new CategoryId(uuid);
        assertThat(id.value()).isEqualTo(uuid);
    }

    @Test
    void shouldThrowForNullId() {
        assertThatThrownBy(() -> new CategoryId(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
