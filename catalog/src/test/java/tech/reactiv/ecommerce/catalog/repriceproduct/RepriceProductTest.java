package tech.reactiv.ecommerce.catalog.repriceproduct;

import org.junit.jupiter.api.Test;
import tech.reactiv.ecommerce.catalog.product.*;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RepriceProductTest {
    InMemoryProducts products = new InMemoryProducts();
    RepriceProductHandler handler = new RepriceProductHandler(products);

    @Test
    void shouldRepriceExistingProduct() {
        var productId = ProductId.create();
        products.list.put(productId, TestProduct.basic(productId));

        handler.handle(new RepriceProductCommand(productId.value(), 300));

        assertThat(products.list.get(productId).state().price().compareTo(new BigDecimal(300))).isEqualTo(0);
    }

    @Test
    void shouldFailToRepriceInvalidProduct() {
        var command = new RepriceProductCommand(UUID.randomUUID(), 300);
        assertThatThrownBy(() -> handler.handle(command)).isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void shouldFailToRepriceProductWithNegativePrice() {
        var productId = ProductId.create();
        products.list.put(productId, TestProduct.basic(productId));

        assertThatThrownBy(() -> handler.handle(new RepriceProductCommand(productId.value(),  -100))).isInstanceOf(IllegalArgumentException.class);
    }
}
