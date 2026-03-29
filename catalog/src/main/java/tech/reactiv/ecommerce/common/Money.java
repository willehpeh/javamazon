package tech.reactiv.ecommerce.common;

import java.math.BigDecimal;

public final class Money {
    public final BigDecimal amount;

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal value() {
        return amount;
    }
}
