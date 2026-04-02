package tech.reactiv.ecommerce.catalog.infrastructure.persistence.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaProducts extends JpaRepository<ProductEntity, UUID> {
    List<ProductEntity> findByCategoryIdAndActiveTrue(UUID categoryId);
    List<ProductEntity> findByActiveTrue();
}
