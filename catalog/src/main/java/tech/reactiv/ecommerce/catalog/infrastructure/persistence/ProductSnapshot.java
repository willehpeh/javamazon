package tech.reactiv.ecommerce.catalog.infrastructure.persistence;

import java.util.UUID;

public record ProductSnapshot(UUID id, String name, String description, int priceInCents, String category) {
}
