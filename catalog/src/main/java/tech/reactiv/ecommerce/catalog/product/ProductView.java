package tech.reactiv.ecommerce.catalog.product;

import java.util.UUID;

public record ProductView(UUID id, String name, String description, int priceInCents, String category, boolean active) {
}
