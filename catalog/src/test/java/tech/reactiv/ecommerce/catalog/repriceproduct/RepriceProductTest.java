package tech.reactiv.ecommerce.catalog.repriceproduct;

import org.junit.jupiter.api.Test;
import tech.reactiv.ecommerce.catalog.product.*;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RepriceProductTest {
    InMemoryProductRepository repository = new InMemoryProductRepository();
    RepriceProductHandler handler = new RepriceProductHandler(repository);

    @Test
    void shouldRepriceExistingProduct() {
        var productId = ProductId.create();
        repository.products.put(productId, dummyProduct(productId));

        handler.handle(new RepriceProductCommand(productId.value(), 300));

        assertThat(repository.products.get(productId).state().priceInCents()).isEqualTo(300);
    }

    @Test
    void shouldFailToRepriceInvalidProduct() {
        var command = new RepriceProductCommand(UUID.randomUUID(), 300);
        assertThatThrownBy(() -> handler.handle(command)).isInstanceOf(ProductNotFoundException.class);
    }

    private Product dummyProduct(ProductId productId) {
        return new Product(productId, new ProductName("Product A"), new ProductDescription("Product A description"), new ProductPrice(100), new ProductCategory("Toys"));
    }
}
