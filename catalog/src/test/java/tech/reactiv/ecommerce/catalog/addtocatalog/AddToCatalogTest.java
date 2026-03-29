package tech.reactiv.ecommerce.catalog.addtocatalog;

import org.junit.jupiter.api.Test;
import tech.reactiv.ecommerce.catalog.product.InMemoryProducts;
import tech.reactiv.ecommerce.catalog.product.ProductId;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AddToCatalogTest {

    InMemoryProducts products = new InMemoryProducts();
    AddToCatalogHandler handler = new AddToCatalogHandler(products);

    @Test
    void shouldAddProductToCatalog() {
        var command = new AddToCatalogCommand("Product A", "Description A", new BigDecimal("1.00"), "Toys");
        ProductId id = handler.handle(command);

        assertThat(id).isNotNull();
        var state = products.list.get(id).state();
        assertThat(state.name()).isEqualTo("Product A");
        assertThat(state.description()).isEqualTo("Description A");
        assertThat(state.price()).isEqualByComparingTo(new BigDecimal("1.00"));
        assertThat(state.category()).isEqualTo("Toys");
    }

    @Test
    void shouldRefuseInvalidProduct() {
        var command = new AddToCatalogCommand("Product A", "Description A", new BigDecimal("-100.00"), "Toys");
        assertThatThrownBy(() -> handler.handle(command))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
