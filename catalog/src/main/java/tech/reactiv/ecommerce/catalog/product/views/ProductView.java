package tech.reactiv.ecommerce.catalog.product.views;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductView(UUID id, String name, String description, BigDecimal price, UUID categoryId, boolean active) {
}
