package tech.reactiv.ecommerce.catalog.infrastructure.persistence.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface JpaProducts extends JpaRepository<ProductEntity, UUID> {

}
