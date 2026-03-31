package tech.reactiv.ecommerce.catalog.product;

import tech.reactiv.ecommerce.common.Money;

import java.math.BigDecimal;

public class ProductPrice {

    private final Money price;

    public ProductPrice(Money price) {
        if (price.value().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.price = price;
    }

    public ProductPrice(String price) {
        this(new Money(price));
    }

    public BigDecimal value() {
        return price.value();
    }
}
