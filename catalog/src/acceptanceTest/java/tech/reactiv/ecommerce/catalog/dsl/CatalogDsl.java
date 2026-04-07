package tech.reactiv.ecommerce.catalog.dsl;

import org.springframework.stereotype.Component;
import tech.reactiv.ecommerce.catalog.driver.CatalogDriver;

import java.util.UUID;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class CatalogDsl {

    private final CatalogDriver driver;

    CatalogDsl(CatalogDriver driver) {
        this.driver = driver;
    }

    public UUID givenCategory(String name) {
        return driver.createCategory(name);
    }

    public UUID addProduct(Consumer<ProductBuilder> customizer) {
        var builder = ProductBuilder.withDefaults();
        customizer.accept(builder);
        if (builder.categoryId() == null) {
            throw new IllegalStateException("Product must have a category");
        }
        return driver.addProduct(builder.name(), builder.description(), builder.price(), builder.categoryId());
    }

    public UUID addProduct() {
        var categoryId = givenCategory(UUID.randomUUID().toString());
        var builder = ProductBuilder.withDefaults();
        return driver.addProduct(builder.name(), builder.description(), builder.price(), categoryId);
    }

    public void verifyProductExists(UUID productId) {
        assertThat(driver.lookupProduct(productId)).isNotNull();
    }

    public void verifyProductHasName(UUID productId, String expectedName) {
        assertThat(driver.lookupProduct(productId).name()).isEqualTo(expectedName);
    }

    public void verifyProductHasDescription(UUID productId, String expectedDescription) {
        assertThat(driver.lookupProduct(productId).description()).isEqualTo(expectedDescription);
    }

    public void verifyProductHasPrice(UUID productId, String expectedPrice) {
        assertThat(driver.lookupProduct(productId).price().value()).isEqualByComparingTo(expectedPrice);
    }

    public void verifyProductIsActive(UUID productId) {
        assertThat(driver.lookupProduct(productId).active()).isTrue();
    }

    public void verifyProductCount(int expectedCount) {
        var products = driver.listProducts();
        assertThat(products).hasSize(expectedCount);
    }
}
