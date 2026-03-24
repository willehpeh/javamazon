package tech.reactiv.ecommerce.catalog.search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.reactiv.ecommerce.catalog.product.InMemoryProductViews;
import tech.reactiv.ecommerce.catalog.product.ProductId;
import tech.reactiv.ecommerce.catalog.product.ProductView;
import tech.reactiv.ecommerce.catalog.product.TestProductView;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchCatalogTest {
    private final InMemoryProductViews views = new InMemoryProductViews();
    private final SearchCatalogHandler handler = new SearchCatalogHandler(views);

    private List<ProductView> productViews;

    @BeforeEach
    void setUp() {
        productViews = TestProductView.basicList(10);
        productViews.forEach(view -> views.products.put(ProductId.from(view.id()), view));
    }

    @Test
    void shouldFindAllProductsIfNoFilterApplied() {
        var foundProducts = handler.handle(new SearchCatalogRequest());
        assertThat(foundProducts).containsAll(productViews);
        assertThat(productViews).containsAll(foundProducts);
    }

    @Test
    void shouldFindAllProductsForCategory() {
        var firstProduct = productViews.getFirst();
        var request = new SearchCatalogRequest().withCategory(firstProduct.category());

        var foundProducts = handler.handle(request);
        assertThat(foundProducts).containsExactly(firstProduct);
    }
}
