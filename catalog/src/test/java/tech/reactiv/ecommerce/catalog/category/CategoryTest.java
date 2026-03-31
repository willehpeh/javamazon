package tech.reactiv.ecommerce.catalog.category;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryTest {
    @Test
    void shouldCreateCategory() {
        var id = CategoryId.create();
        var category = new Category(id, new CategoryName("Toys"));
        var state = category.state();
        assertThat(state.id()).isEqualTo(id.value());
        assertThat(state.name()).isEqualTo("Toys");
    }
}
