package tech.reactiv.ecommerce.catalog.discontinueproduct;

import org.junit.jupiter.api.Test;
import tech.reactiv.ecommerce.catalog.product.InMemoryProducts;
import tech.reactiv.ecommerce.catalog.product.ProductId;
import tech.reactiv.ecommerce.catalog.product.ProductNotFoundException;
import tech.reactiv.ecommerce.catalog.product.TestProduct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DiscontinueProductTest {

    InMemoryProducts repository = new InMemoryProducts();
    DiscontinueProductHandler handler = new DiscontinueProductHandler(repository);

    @Test
    void shouldDiscontinueProduct() {
        var productId = ProductId.create();
        var product = TestProduct.basic(productId);
        repository.products.put(productId, product);

        var command = new DiscontinueProductCommand(productId);
        handler.handle(command);

        assertThat(repository.products.get(productId).state().active()).isFalse();
    }

    @Test
    void shouldFailToDiscontinueNonExistentProduct() {
        var productId = ProductId.create();
        var command = new DiscontinueProductCommand(productId);

        assertThatThrownBy(() -> handler.handle(command))
            .isInstanceOf(ProductNotFoundException.class);
    }
}
