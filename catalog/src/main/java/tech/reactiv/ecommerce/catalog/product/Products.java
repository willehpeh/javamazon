package tech.reactiv.ecommerce.catalog.product;

import java.util.Optional;

public interface Products {
    void add(Product product);

    void modify(Product product);

    Optional<Product> productWithId(ProductId id);

    boolean contains(ProductId id);
}
