package tech.reactiv.ecommerce.catalog.product;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryProducts implements Products {
    public final Map<ProductId, Product> list = new HashMap<>();

    @Override
    public void add(Product product) {
        list.put(product.id(), product);
    }

    @Override
    public void modify(Product product) {
        list.put(product.id(), product);
    }

    @Override
    public Optional<Product> productWithId(ProductId id) {
        return Optional.ofNullable(list.get(id));
    }
}
