package tech.reactiv.ecommerce.catalog.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryProductViews implements ProductViews {
    public final Map<ProductId, ProductView> list = new HashMap<>();

    @Override
    public Optional<ProductView> withId(ProductId id) {
        return Optional.ofNullable(list.get(id));
    }

    @Override
    public List<ProductView> all() {
        return List.copyOf(list.values());
    }
}
