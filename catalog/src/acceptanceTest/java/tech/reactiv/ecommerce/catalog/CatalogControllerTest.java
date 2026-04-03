package tech.reactiv.ecommerce.catalog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.reactiv.ecommerce.catalog.dsl.CatalogDsl;

import static org.assertj.core.api.Assertions.assertThat;

class CatalogControllerTest extends AcceptanceTest {

    @Autowired
    private CatalogDsl catalog;

    @Test
    void createsAndRetrievesProduct() {
        var productId = catalog.addProduct(p -> p
                .inCategory("Electronics")
                .withName("Laptop")
                .withDescription("A powerful laptop")
                .withPrice("999.99"));
        catalog.verifyProduct(productId, product -> {
            assertThat(product.name()).isEqualTo("Laptop");
            assertThat(product.description()).isEqualTo("A powerful laptop");
            assertThat(product.price().value()).isEqualByComparingTo("999.99");
            assertThat(product.active()).isTrue();
        });
    }
}
