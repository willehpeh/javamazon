package tech.reactiv.ecommerce.catalog.addtocatalog;

import org.junit.jupiter.api.Test;
import tech.reactiv.ecommerce.catalog.infrastructure.persistence.ProductSnapshot;
import tech.reactiv.ecommerce.catalog.product.ProductId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AddToCatalogTest {

    InMemoryProductRepository repository = new InMemoryProductRepository();
    AddToCatalogHandler handler = new AddToCatalogHandler(repository);

    @Test
    void shouldAddProductToCatalog() {
        var command = new AddToCatalogCommand("Product A", "Description A", 100);
        ProductId id = handler.handle(command);

        assertThat(id).isNotNull();
        ProductSnapshot product = repository.products.get(id);
        assertThat(product.name()).isEqualTo("Product A");
        assertThat(product.description()).isEqualTo("Description A");
        assertThat(product.priceInCents()).isEqualTo(100);
    }

    @Test
    void shouldRefuseInvalidProduct() {
        var command = new AddToCatalogCommand("Product A", "Description A", -100);
        assertThatThrownBy(() -> handler.handle(command))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
