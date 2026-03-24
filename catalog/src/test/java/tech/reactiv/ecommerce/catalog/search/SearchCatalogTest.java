package tech.reactiv.ecommerce.catalog.search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.reactiv.ecommerce.catalog.product.InMemoryProductViews;
import tech.reactiv.ecommerce.catalog.product.ProductId;
import tech.reactiv.ecommerce.catalog.product.ProductView;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchCatalogTest {
    private final InMemoryProductViews views = new InMemoryProductViews();
    private final SearchCatalogHandler handler = new SearchCatalogHandler(views);

    private List<ProductView> productViews;

    @BeforeEach
    void setUp() {
        productViews = IntStream.range(0, 10)
                .mapToObj(i -> {
                    var id = ProductId.create();
                    var suffix = UUID.randomUUID().toString().substring(0, 8);
                    var view = new ProductView(id.value(), "Product " + suffix, "Description " + suffix, 100 + i, "Category " + suffix, true);
                    views.products.put(id, view);
                    return view;
                })
                .toList();
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
