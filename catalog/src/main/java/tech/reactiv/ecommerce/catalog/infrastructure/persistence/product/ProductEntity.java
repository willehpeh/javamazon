package tech.reactiv.ecommerce.catalog.infrastructure.persistence.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import tech.reactiv.ecommerce.catalog.category.CategoryId;
import tech.reactiv.ecommerce.catalog.product.*;
import tech.reactiv.ecommerce.common.Money;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "category_id", nullable = false)
    private UUID categoryId;

    @Column(nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected ProductEntity() {
    }

    static ProductEntity fromProductState(Product.State state) {
        var entity = new ProductEntity();
        entity.id = state.id();
        entity.name = state.name();
        entity.description = state.description();
        entity.price = state.price().value();
        entity.categoryId = state.categoryId();
        entity.active = state.active();
        entity.createdAt = LocalDateTime.now();
        return entity;
    }

    Product toProduct() {
        return new Product(
                new ProductId(id),
                new ProductName(name),
                new ProductDescription(description),
                new ProductPrice(new Money(price)),
                new CategoryId(categoryId)
        );
    }
}
