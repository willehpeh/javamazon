package tech.reactiv.ecommerce.catalog.lookupproduct;

import org.junit.jupiter.api.Test;
import tech.reactiv.ecommerce.catalog.product.views.InMemoryProductViews;
import tech.reactiv.ecommerce.catalog.product.ProductId;
import tech.reactiv.ecommerce.catalog.product.views.ProductView;
import tech.reactiv.ecommerce.catalog.product.views.TestProductView;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class LookupProductTest {
    private final InMemoryProductViews views = new InMemoryProductViews();
    private final LookupProductHandler handler = new LookupProductHandler(views);

    @Test
    void shouldLookupProduct() {
        var productId = ProductId.create();
        var productView = TestProductView.basic(productId);
        views.list.put(productId, productView);

        Optional<ProductView> found = handler.handle(new LookupProductRequest(productId.value()));
        assertThat(found.orElseThrow()).isEqualTo(productView);
    }

    @Test
    void shouldNotFindNonExistentProduct() {
        Optional<ProductView> found = handler.handle(new LookupProductRequest(ProductId.create().value()));
        assertThat(found).isEmpty();
    }
}
