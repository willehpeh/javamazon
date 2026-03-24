package tech.reactiv.ecommerce.catalog.lookupproduct;

import org.junit.jupiter.api.Test;
import tech.reactiv.ecommerce.catalog.product.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class LookupProductTest {
    private final InMemoryProductRepository repository = new InMemoryProductRepository();
    private final LookupProductHandler handler = new LookupProductHandler(repository);

    @Test
    void shouldLookupProduct() {
        var productId = ProductId.create();
        var product = TestProduct.basic(productId);
        repository.products.put(productId, product);

        Optional<Product> foundProduct = handler.handle(productId);
        assertThat(foundProduct.orElseThrow()).isEqualTo(product);
    }

    @Test
    void shouldNotFindNonExistentProduct() {
        Optional<Product> foundProduct = handler.handle(ProductId.create());
        assertThat(foundProduct).isEmpty();
    }
}
