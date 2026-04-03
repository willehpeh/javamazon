package tech.reactiv.ecommerce.catalog.product;

import tech.reactiv.ecommerce.common.Money;

import java.math.BigDecimal;

public record ProductPrice(Money value) {

    public ProductPrice {
        if (value.value().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
    }

    public ProductPrice(String value) {
        this(new Money(value));
    }
}
