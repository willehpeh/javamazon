package tech.reactiv.ecommerce.catalog.search;

import org.junit.jupiter.api.Test;
import tech.reactiv.ecommerce.catalog.product.InMemoryProducts;
import tech.reactiv.ecommerce.catalog.product.Product;
import tech.reactiv.ecommerce.catalog.product.TestProduct;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchCatalogTest {
    private final InMemoryProducts repository = new InMemoryProducts();
    private final SearchCatalogHandler handler = new SearchCatalogHandler(repository);

    @Test
    void shouldFindAllProductsIfNoFilterApplied() {
        List<Product> products = TestProduct.basicList(10);
        products.forEach((product) -> repository.products.put(product.id(), product));

        var foundProducts = handler.handle(new SearchCatalogRequest());
        assertThat(foundProducts).containsAll(products);
        assertThat(products).containsAll(foundProducts);
    }
}
