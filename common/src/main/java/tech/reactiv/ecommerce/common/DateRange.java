package tech.reactiv.ecommerce.common;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public record DateRange(LocalDate startDate, LocalDate endDate) {
    public DateRange {
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start cannot be after end");
        }
    }

    public boolean contains(LocalDate containedDate) {
        return isBetweenStartAndEnd(containedDate) || isStartOrEnd(containedDate);
    }

    public boolean overlaps(DateRange other) {
        return !other.startDate.isAfter(endDate) && !other.endDate.isBefore(startDate);
    }

    public Period duration() {
        return Period.between(startDate, endDate);
    }

    private boolean isStartOrEnd(LocalDate containedDate) {
        return containedDate.equals(startDate) || containedDate.equals(endDate);
    }

    private boolean isBetweenStartAndEnd(LocalDate containedDate) {
        return containedDate.isAfter(startDate) && containedDate.isBefore(endDate);
    }
}
