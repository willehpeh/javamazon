package tech.reactiv.ecommerce.catalog.addtocatalog;

import tech.reactiv.ecommerce.catalog.infrastructure.persistence.ProductSnapshot;
import tech.reactiv.ecommerce.catalog.product.Product;
import tech.reactiv.ecommerce.catalog.product.ProductId;
import tech.reactiv.ecommerce.catalog.product.ProductRepository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryProductRepository implements ProductRepository {
    public Map<ProductId, ProductSnapshot> products = new HashMap<>();

    @Override
    public void add(Product product) {
        var productState = product.state();
        var snapshot = new ProductSnapshot(productState.name(), productState.description(), productState.priceInCents(), productState.category());
        products.put(product.id(), snapshot);
    }
}
