package tech.reactiv.ecommerce.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Money(BigDecimal value) {
    public Money {
        if (value.scale() > 2) {
            throw new IllegalArgumentException("Money must have 2 decimal places");
        }
        value = value.setScale(2, RoundingMode.HALF_UP);
    }

    public Money(String value) {
        this(new BigDecimal(value));
    }

    public Money(int value) {
        this(BigDecimal.valueOf(value));
    }

    public Money add(Money other) {
        return new Money(value.add(other.value));
    }

    public Money subtract(Money other) {
        return new Money(value.subtract(other.value));
    }

    public Money multiply(int multiplier) {
        return new Money(value.multiply(BigDecimal.valueOf(multiplier)));
    }

    public Money percent(int percentage) {
        if (percentage < 0) {
            throw new IllegalArgumentException("Percentage must be positive");
        }
        BigDecimal percentageAsDecimal = BigDecimal.valueOf(percentage, 2).divide(BigDecimal.valueOf(100, 2), 2, RoundingMode.HALF_UP);
        BigDecimal scaledResult = value.multiply(percentageAsDecimal).setScale(2, RoundingMode.HALF_UP);
        return new Money(scaledResult);
    }
}
