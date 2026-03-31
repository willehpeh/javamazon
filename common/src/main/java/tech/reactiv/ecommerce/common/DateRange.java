package tech.reactiv.ecommerce.common;

import java.time.LocalDate;
import java.time.Period;

public record DateRange(LocalDate startDate, LocalDate endDate) {
    public DateRange {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start and end dates cannot be null");
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
