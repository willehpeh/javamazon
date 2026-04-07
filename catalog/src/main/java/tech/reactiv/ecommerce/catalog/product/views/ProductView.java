package tech.reactiv.ecommerce.catalog.product.views;

import tech.reactiv.ecommerce.common.Money;

import java.util.UUID;

public record ProductView(UUID id, String name, String description, Money price, UUID categoryId, boolean active) {

    public ProductView withPrice(Money newPrice) {
        return new ProductView(id, name, description, newPrice, categoryId, active);
    }
}
