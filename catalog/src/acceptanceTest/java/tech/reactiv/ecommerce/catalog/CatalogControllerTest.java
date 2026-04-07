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
        var productId = catalog.addProduct(p -> p
                .inCategory(categoryId)
                .withName("Laptop")
                .withDescription("A powerful laptop")
                .withPrice("999.99"));
        catalog.verifyProductExists(productId);
        catalog.verifyProductHasName(productId, "Laptop");
        catalog.verifyProductHasDescription(productId, "A powerful laptop");
        catalog.verifyProductHasPrice(productId, "999.99");
        catalog.verifyProductIsActive(productId);
    }

    @Test
    void retrievesAllProducts() {
        catalog.addProduct();
        catalog.addProduct();
        catalog.addProduct();
        catalog.addProduct();

        catalog.verifyProductCount(4);
    }
}
