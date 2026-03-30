package tech.reactiv.ecommerce.catalog.addproduct;

import java.math.BigDecimal;
import java.util.UUID;

public record AddProductCommand(UUID productId, String productName, String description, BigDecimal price, String category) {
}
