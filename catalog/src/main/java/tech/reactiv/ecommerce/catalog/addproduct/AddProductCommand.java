package tech.reactiv.ecommerce.catalog.addproduct;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import tech.reactiv.ecommerce.shared.mediator.Command;

import java.math.BigDecimal;
import java.util.UUID;

public record AddProductCommand(@NotNull UUID productId,
                                @NotBlank String productName,
                                String description,
                                @NotNull @Positive BigDecimal price,
                                UUID categoryId) implements Command {
}
