package tech.reactiv.ecommerce.catalog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.reactiv.ecommerce.catalog.category.CategoryId;
import tech.reactiv.ecommerce.catalog.dsl.CatalogDsl;
import tech.reactiv.ecommerce.catalog.promotion.ByCategory;

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
        catalog.thenProductCanBeRetrieved(productId, p -> p
                .hasName("Laptop")
                .hasDescription("A powerful laptop")
                .hasPrice("999.99")
                .isInCategory(categoryId)
        );
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

    @Test
    void retrievesProductWithPromotionalPrice() {
        var categoryId = catalog.givenCategory("Electronics");
        var productId = catalog.givenProduct(p -> p.inCategory(categoryId).withPrice("100.00"));
        catalog.whenPromotionScheduled(p -> p
                .withDiscountPercent(50)
                .withTarget(new ByCategory(new CategoryId(categoryId)))
        );

        catalog.thenProductCanBeRetrieved(productId, p -> p.hasPrice("50.00"));
    }
}
