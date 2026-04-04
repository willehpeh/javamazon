package tech.reactiv.ecommerce.catalog.addproduct;

import org.junit.jupiter.api.Test;
import tech.reactiv.ecommerce.catalog.product.InMemoryProducts;
import tech.reactiv.ecommerce.catalog.product.ProductAlreadyExistsException;
import tech.reactiv.ecommerce.catalog.product.ProductId;
import tech.reactiv.ecommerce.common.Money;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AddProductTest {

    InMemoryProducts products = new InMemoryProducts();
    AddProductHandler handler = new AddProductHandler(products);

    @Test
    void shouldAddProduct() {
        var id = ProductId.create();
        var categoryId = UUID.randomUUID();
        var command = new AddProductCommand(id.value(), "Product A", "Description A", new BigDecimal("1.00"), categoryId);
        handler.handle(command);

        var state = products.list.get(id).state();
        assertThat(state.name()).isEqualTo("Product A");
        assertThat(state.description()).isEqualTo("Description A");
        assertThat(state.price()).isEqualTo(new Money("1.00"));
        assertThat(state.categoryId()).isEqualTo(categoryId);
    }

    @Test
    void shouldRefuseInvalidProduct() {
        var command = new AddProductCommand(UUID.randomUUID(), "Product A", "Description A", new BigDecimal("-100.00"), UUID.randomUUID());
        assertThatThrownBy(() -> handler.handle(command))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRefuseDuplicateProduct() {
        var id = ProductId.create();
        var command = new AddProductCommand(id.value(), "Product A", "Description A", new BigDecimal("1.00"), UUID.randomUUID());
        handler.handle(command);

        assertThatThrownBy(() -> handler.handle(command))
            .isInstanceOf(ProductAlreadyExistsException.class);
    }
}
