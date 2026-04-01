package tech.reactiv.ecommerce.catalog.repriceproduct;

import tech.reactiv.ecommerce.shared.mediator.Command;

import java.math.BigDecimal;
import java.util.UUID;

public record RepriceProductCommand(UUID productId, BigDecimal newPrice) implements Command {
}
