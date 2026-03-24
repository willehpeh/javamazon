package tech.reactiv.ecommerce.catalog.product.views;

import tech.reactiv.ecommerce.catalog.product.ProductId;

import java.util.List;
import java.util.Optional;

public interface ProductViews {
    Optional<ProductView> withId(ProductId id);

    List<ProductView> all();
}
