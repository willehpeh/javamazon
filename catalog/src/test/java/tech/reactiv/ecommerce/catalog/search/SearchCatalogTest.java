package tech.reactiv.ecommerce.catalog.search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tech.reactiv.ecommerce.catalog.product.InMemoryProductViews;
import tech.reactiv.ecommerce.catalog.product.ProductId;
import tech.reactiv.ecommerce.catalog.product.ProductView;
import tech.reactiv.ecommerce.catalog.product.TestProductView;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchCatalogTest {
    private final InMemoryProductViews views = new InMemoryProductViews();
    private final SearchCatalogHandler handler = new SearchCatalogHandler(views);

    private List<ProductView> dummyViews;

    @BeforeEach
    void setUp() {
        dummyViews = TestProductView.basicList(10);
        dummyViews.forEach(view -> views.list.put(ProductId.from(view.id()), view));
    }

    @Test
    void shouldFindAllProductsIfNoFilterApplied() {
        var foundProducts = handler.handle(new SearchCatalogRequest());
        assertThat(foundProducts).containsAll(dummyViews);
        assertThat(dummyViews).containsAll(foundProducts);
    }

    @Test
    void shouldFindAllProductsForCategory() {
        var firstProduct = dummyViews.getFirst();
        var newProductId = ProductId.create();
        var newProductWithSameCategory = TestProductView.withCategory(TestProductView.basic(newProductId), firstProduct.category());
        views.list.put(newProductId, newProductWithSameCategory);
        var request = new SearchCatalogRequest().withCategory(firstProduct.category());

        var foundProducts = handler.handle(request);
        assertThat(foundProducts).containsExactlyInAnyOrder(firstProduct, newProductWithSameCategory);
    }

    @ParameterizedTest
    @MethodSource("nonMatchingRequests")
    void shouldFindNoProductsIfNoneMatchRequest(SearchCatalogRequest request) {
        var foundProducts = handler.handle(request);
        assertThat(foundProducts).isEmpty();
    }

    static Stream<SearchCatalogRequest> nonMatchingRequests() {
        return Stream.of(
                new SearchCatalogRequest().withCategory("Non-existent category"),
                new SearchCatalogRequest().withCategory("Another non-existent category")
        );
    }
}
