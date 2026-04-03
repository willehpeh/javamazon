package tech.reactiv.ecommerce.catalog.dsl;

import org.springframework.stereotype.Component;
import tech.reactiv.ecommerce.catalog.driver.CatalogDriver;
import tech.reactiv.ecommerce.catalog.product.views.ProductView;

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
        if (builder.category() != null) {
            var categoryId = givenCategory(builder.category());
            return driver.addProduct(builder.name(), builder.description(), builder.price(), categoryId);
        }
        throw new IllegalStateException("Product must have a category");
    }

    public void verifyProduct(UUID productId, Consumer<ProductView> assertions) {
        var product = driver.lookupProduct(productId);
        assertThat(product).isNotNull();
        assertions.accept(product);
    }
}
