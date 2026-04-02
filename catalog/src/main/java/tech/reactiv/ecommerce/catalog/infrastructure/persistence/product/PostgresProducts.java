package tech.reactiv.ecommerce.catalog.infrastructure.persistence.product;

import org.springframework.stereotype.Repository;
import tech.reactiv.ecommerce.catalog.product.Product;
import tech.reactiv.ecommerce.catalog.product.ProductId;
import tech.reactiv.ecommerce.catalog.product.Products;

import java.util.Optional;

@Repository
class PostgresProducts implements Products {

    private final JpaProducts jpaProducts;

    public PostgresProducts(JpaProducts jpaProducts) {
        this.jpaProducts = jpaProducts;
    }

    @Override
    public void addOrUpdate(Product product) {
        jpaProducts.save(ProductEntity.fromProductState(product.state()));
    }

    @Override
    public Optional<Product> productWithId(ProductId id) {
        return jpaProducts.findById(id.value())
                .map(ProductEntity::toProduct);
    }
}
