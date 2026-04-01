package tech.reactiv.ecommerce.catalog.addproduct;

import tech.reactiv.ecommerce.shared.mediator.Command;

import java.math.BigDecimal;
import java.util.UUID;

public record AddProductCommand(UUID productId,
                                String productName,
                                String description,
                                BigDecimal price,
                                UUID categoryId) implements Command<Void> {
}
