package tech.reactiv.ecommerce.catalog.dsl;

import tech.reactiv.ecommerce.catalog.product.views.ProductView;
import tech.reactiv.ecommerce.common.Money;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public record ProductViewAssertions(ProductView productView) {
    public void hasName(String name) {
        assertThat(productView.name()).isEqualTo(name);
    }

    public void hasDescription(String description) {
        assertThat(productView.description()).isEqualTo(description);
    }

    public void hasPrice(String price) {
        assertThat(productView.price()).isEqualTo(new Money(price));
    }

    public void isInCategory(UUID category) {
        assertThat(productView.categoryId()).isEqualTo(category);
    }
}
