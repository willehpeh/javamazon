package tech.reactiv.ecommerce.catalog.repriceproduct;

import java.math.BigDecimal;
import java.util.UUID;

public record RepriceProductCommand(UUID productId, BigDecimal newPrice) {
}
