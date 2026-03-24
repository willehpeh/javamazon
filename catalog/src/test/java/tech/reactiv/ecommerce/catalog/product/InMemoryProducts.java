package tech.reactiv.ecommerce.catalog.product;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryProducts implements Products {
    public final Map<ProductId, Product> products = new HashMap<>();

    @Override
    public void addOrUpdate(Product product) {
        products.put(product.id(), product);
    }

    @Override
    public Optional<Product> productWithId(ProductId id) {
        return Optional.ofNullable(products.get(id));
    }
}
