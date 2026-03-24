package tech.reactiv.ecommerce.catalog.lookupproduct;

import org.junit.jupiter.api.Test;
import tech.reactiv.ecommerce.catalog.product.InMemoryProductViews;
import tech.reactiv.ecommerce.catalog.product.ProductId;
import tech.reactiv.ecommerce.catalog.product.ProductView;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class LookupProductTest {
    private final InMemoryProductViews views = new InMemoryProductViews();
    private final LookupProductHandler handler = new LookupProductHandler(views);

    @Test
    void shouldLookupProduct() {
        var productId = ProductId.create();
        var productView = new ProductView(productId.value(), "Widget", "A fine widget", 999, "Gadgets", true);
        views.products.put(productId, productView);

        Optional<ProductView> found = handler.handle(productId);
        assertThat(found.orElseThrow()).isEqualTo(productView);
    }

    @Test
    void shouldNotFindNonExistentProduct() {
        Optional<ProductView> found = handler.handle(ProductId.create());
        assertThat(found).isEmpty();
    }
}
