package tech.reactiv.ecommerce.catalog.repriceproduct;

import java.util.UUID;

public record RepriceProductCommand(UUID productId, int newPriceInCents) {
}
