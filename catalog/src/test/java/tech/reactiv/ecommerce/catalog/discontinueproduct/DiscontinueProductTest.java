package tech.reactiv.ecommerce.catalog.discontinueproduct;

import org.junit.jupiter.api.Test;
import tech.reactiv.ecommerce.catalog.product.InMemoryProducts;
import tech.reactiv.ecommerce.catalog.product.ProductId;
import tech.reactiv.ecommerce.catalog.product.ProductNotFoundException;
import tech.reactiv.ecommerce.catalog.product.TestProduct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DiscontinueProductTest {

    InMemoryProducts products = new InMemoryProducts();
    DiscontinueProductHandler handler = new DiscontinueProductHandler(products);

    @Test
    void shouldDiscontinueProduct() {
        var productId = ProductId.create();
        var product = TestProduct.basic(productId);
        products.list.put(productId, product);

        var command = new DiscontinueProductCommand(productId);
        handler.handle(command);

        assertThat(products.list.get(productId).state().active()).isFalse();
    }

    @Test
    void shouldFailToDiscontinueNonExistentProduct() {
        var productId = ProductId.create();
        var command = new DiscontinueProductCommand(productId);

        assertThatThrownBy(() -> handler.handle(command))
            .isInstanceOf(ProductNotFoundException.class);
    }
}
