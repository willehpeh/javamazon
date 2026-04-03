package tech.reactiv.ecommerce.catalog.infrastructure.persistence.product.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;
import tech.reactiv.ecommerce.catalog.product.views.ProductView;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Immutable
@Table(name = "product")
class ProductViewEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "category_id", nullable = false)
    private UUID categoryId;

    @Column(name = "active")
    private boolean active;

    protected ProductViewEntity() {
    }

    ProductView toProductView() {
        return new ProductView(id, name, description, price, categoryId, active);
    }
}
