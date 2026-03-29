package tech.reactiv.ecommerce.common;

import java.math.BigDecimal;

public final class Money {
    public final BigDecimal amount;

    public Money(BigDecimal amount) {
        if (amount.scale() != 2) {
            throw new IllegalArgumentException("Money must have 2 decimal places");
        }
        this.amount = amount;
    }

    public Money(String amount) {
        this(new BigDecimal(amount));
    }

    public BigDecimal value() {
        return amount;
    }
}
