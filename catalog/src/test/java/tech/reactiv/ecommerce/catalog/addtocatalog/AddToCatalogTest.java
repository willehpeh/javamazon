package tech.reactiv.ecommerce.catalog.addtocatalog;

import org.junit.jupiter.api.Test;
import tech.reactiv.ecommerce.catalog.product.InMemoryProductRepository;
import tech.reactiv.ecommerce.catalog.product.ProductId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AddToCatalogTest {

    InMemoryProductRepository repository = new InMemoryProductRepository();
    AddToCatalogHandler handler = new AddToCatalogHandler(repository);

    @Test
    void shouldAddProductToCatalog() {
        var command = new AddToCatalogCommand("Product A", "Description A", 100, "Toys");
        ProductId id = handler.handle(command);

        assertThat(id).isNotNull();
        var state = repository.products.get(id).state();
        assertThat(state.name()).isEqualTo("Product A");
        assertThat(state.description()).isEqualTo("Description A");
        assertThat(state.priceInCents()).isEqualTo(100);
        assertThat(state.category()).isEqualTo("Toys");
    }

    @Test
    void shouldRefuseInvalidProduct() {
        var command = new AddToCatalogCommand("Product A", "Description A", -100, "Toys");
        assertThatThrownBy(() -> handler.handle(command))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
