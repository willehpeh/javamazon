package tech.reactiv.ecommerce.catalog.product;

import java.util.List;
import java.util.Optional;

public interface Products {
    void addOrUpdate(Product product);

    Optional<Product> productWithId(ProductId id);

    List<Product> all();
}
