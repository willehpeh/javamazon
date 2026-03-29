package tech.reactiv.ecommerce.catalog.addtocatalog;

import java.math.BigDecimal;

public record AddToCatalogCommand(String productName, String description, BigDecimal price, String category) {
}
