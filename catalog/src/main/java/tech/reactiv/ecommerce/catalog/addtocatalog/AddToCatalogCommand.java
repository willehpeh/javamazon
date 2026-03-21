package tech.reactiv.ecommerce.catalog.addtocatalog;

public record AddToCatalogCommand(String productName, String description, int priceInCents) {
}
