package tech.reactiv.ecommerce.catalog.product;

import java.util.List;
import java.util.Optional;

public interface ProductViews {
    Optional<ProductView> withId(ProductId id);

    List<ProductView> all();
}
