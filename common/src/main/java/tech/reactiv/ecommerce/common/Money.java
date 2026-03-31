package tech.reactiv.ecommerce.common;

import java.math.BigDecimal;

public record Money(BigDecimal value) {
    public Money {
        if (value.scale() != 2) {
            throw new IllegalArgumentException("Money must have 2 decimal places");
        }
    }

    public Money(String value) {
        this(new BigDecimal(value));
    }
}
