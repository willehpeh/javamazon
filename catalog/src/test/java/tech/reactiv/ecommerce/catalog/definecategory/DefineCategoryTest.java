package tech.reactiv.ecommerce.catalog.definecategory;

import org.junit.jupiter.api.Test;
import tech.reactiv.ecommerce.catalog.category.InMemoryCategories;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class DefineCategoryTest {
    private final InMemoryCategories categories = new InMemoryCategories();
    private final DefineCategoryHandler handler = new DefineCategoryHandler(categories);

    @Test
    void shouldDefineCategory() {
        var command = new DefineCategoryCommand(UUID.randomUUID(), "test-category");

        handler.handle(command);

        var definedCategory = categories.list.get(command.id());
        assertThat(definedCategory.id().value()).isEqualTo(command.id());
        assertThat(definedCategory.state().name()).isEqualTo("test-category");
    }
}
