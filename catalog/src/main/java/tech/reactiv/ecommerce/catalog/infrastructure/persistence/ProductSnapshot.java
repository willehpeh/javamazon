package tech.reactiv.ecommerce.catalog.infrastructure.persistence;

public record ProductSnapshot(String name, String description, int priceInCents, String category) {
}
