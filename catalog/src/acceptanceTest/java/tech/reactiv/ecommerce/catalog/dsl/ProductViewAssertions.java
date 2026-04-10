package tech.reactiv.ecommerce.catalog.dsl;

import tech.reactiv.ecommerce.catalog.product.views.ProductView;
import tech.reactiv.ecommerce.common.Money;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public record ProductViewAssertions(ProductView productView) {
    public ProductViewAssertions hasName(String name) {
        assertThat(productView.name()).isEqualTo(name);
        return this;
    }

    public ProductViewAssertions hasDescription(String description) {
        assertThat(productView.description()).isEqualTo(description);
        return this;
    }

    public ProductViewAssertions hasPrice(String price) {
        assertThat(productView.price()).isEqualTo(new Money(price));
        return this;
    }

    public ProductViewAssertions isInCategory(UUID category) {
        assertThat(productView.categoryId()).isEqualTo(category);
        return this;
    }
}
