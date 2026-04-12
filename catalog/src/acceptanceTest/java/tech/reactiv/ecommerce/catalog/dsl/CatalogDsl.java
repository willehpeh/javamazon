package tech.reactiv.ecommerce.catalog.dsl;

import org.springframework.stereotype.Component;
import tech.reactiv.ecommerce.catalog.driver.CatalogDriver;
import tech.reactiv.ecommerce.catalog.fixtures.CatalogFixtures;

import tech.reactiv.ecommerce.catalog.product.views.ProductView;

import java.util.UUID;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class CatalogDsl {

    private final CatalogDriver driver;
    private final CatalogFixtures fixtures;

    CatalogDsl(CatalogDriver driver, CatalogFixtures fixtures) {
        this.driver = driver;
        this.fixtures = fixtures;
    }

    public UUID givenCategory(String name) {
        return fixtures.insertCategory(name);
    }

    public UUID givenProduct(Consumer<ProductBuilder> customizer) {
        var builder = ProductBuilder.withDefaults();
        customizer.accept(builder);
        return fixtures.insertProduct(builder.name(), builder.description(), builder.price(), builder.categoryId());
    }

    public UUID whenProductAdded(Consumer<ProductBuilder> customizer) {
        var builder = ProductBuilder.withDefaults();
        customizer.accept(builder);
        if (builder.categoryId() == null) {
            throw new IllegalStateException("Product must have a category");
        }
        return driver.addProduct(builder.name(), builder.description(), builder.price(), builder.categoryId());
    }

    public void thenAllProductsAre(UUID... expectedIds) {
        var products = driver.listProducts();
        assertThat(products)
                .extracting(ProductView::id)
                .containsExactlyInAnyOrder(expectedIds);
    }

    public void thenProductCanBeRetrieved(UUID productId, Consumer<ProductViewAssertions> consumer) {
        var product = driver.lookupProduct(productId);
        consumer.accept(new ProductViewAssertions(product));
    }

    public void thenProductDoesNotExist(UUID productId) {
        driver.expectNoProduct(productId);
    }

    public void whenPromotionScheduled(Consumer<PromotionBuilder> customizer) {
        var builder = PromotionBuilder.withDefaults();
        customizer.accept(builder);
        driver.schedulePromotion(builder.description(), builder.discountPercent(), builder.startDate(), builder.endDate(), builder.target());
    }

    public void whenProductDiscontinued(UUID productId) {
        driver.discontinueProduct(productId);
    }
}
