package tech.reactiv.ecommerce.catalog.discontinueproduct;

import tech.reactiv.ecommerce.shared.mediator.Command;

import java.util.UUID;

public record DiscontinueProductCommand(UUID productId) implements Command {
}
