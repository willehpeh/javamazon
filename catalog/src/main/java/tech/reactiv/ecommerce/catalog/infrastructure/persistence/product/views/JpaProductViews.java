package tech.reactiv.ecommerce.catalog.infrastructure.persistence.product.views;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

interface JpaProductViews extends JpaRepository<ProductViewEntity, UUID> {
    List<ProductViewEntity> findByActiveTrue();
    List<ProductViewEntity> findByCategoryIdAndActiveTrue(UUID categoryId);
}
