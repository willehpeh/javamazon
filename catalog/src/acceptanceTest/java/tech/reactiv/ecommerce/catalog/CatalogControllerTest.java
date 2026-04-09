package tech.reactiv.ecommerce.catalog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.reactiv.ecommerce.catalog.dsl.CatalogDsl;

class CatalogControllerTest extends AcceptanceTest {

    @Autowired
    private CatalogDsl catalog;

    @Test
    void createsAndRetrievesProduct() {
        var categoryId = catalog.givenCategory("Electronics");
        var productId = catalog.whenProductAdded(product -> product
                .inCategory(categoryId)
                .withName("Laptop")
                .withDescription("A powerful laptop")
                .withPrice("999.99"));
        catalog.thenProductCanBeRetrieved(productId, p -> {
            p.hasName("Laptop");
            p.hasDescription("A powerful laptop");
            p.hasPrice("999.99");
            p.isInCategory(categoryId);
        });
    }

    @Test
    void retrievesAllProducts() {
        var categoryId = catalog.givenCategory("Electronics");
        catalog.givenProduct(p -> p.inCategory(categoryId));
        catalog.givenProduct(p -> p.inCategory(categoryId));
        catalog.givenProduct(p -> p.inCategory(categoryId));
        catalog.givenProduct(p -> p.inCategory(categoryId));

        catalog.thenTotalNumberOfProductsIs(4);
    }
}
