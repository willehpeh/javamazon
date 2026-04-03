package tech.reactiv.ecommerce.catalog.infrastructure.persistence.product.views;

import org.springframework.stereotype.Repository;
import tech.reactiv.ecommerce.catalog.category.CategoryId;
import tech.reactiv.ecommerce.catalog.product.ProductId;
import tech.reactiv.ecommerce.catalog.product.views.ProductView;
import tech.reactiv.ecommerce.catalog.product.views.ProductViews;

import java.util.List;
import java.util.Optional;

@Repository
class PostgresProductViews implements ProductViews {

    private final JpaProductViews jpaProductViews;

    PostgresProductViews(JpaProductViews jpaProductViews) {
        this.jpaProductViews = jpaProductViews;
    }

    @Override
    public Optional<ProductView> withId(ProductId id) {
        return jpaProductViews.findById(id.value()).map(ProductViewEntity::toProductView);
    }

    @Override
    public List<ProductView> all() {
        return jpaProductViews.findByActiveTrue().stream()
                .map(ProductViewEntity::toProductView)
                .toList();
    }

    @Override
    public List<ProductView> forCategory(CategoryId categoryId) {
        return jpaProductViews.findByCategoryIdAndActiveTrue(categoryId.value()).stream()
                .map(ProductViewEntity::toProductView)
                .toList();
    }
}
