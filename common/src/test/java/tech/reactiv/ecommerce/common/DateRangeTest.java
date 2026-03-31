package tech.reactiv.ecommerce.common;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.Period;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DateRangeTest {
    @Test
    void shouldCreateDateRangeWithEndAfterStart() {
        var startDate = LocalDate.now();
        var endDate = startDate.plusDays(7);
        var dateRange = new DateRange(startDate, endDate);

        assertThat(dateRange).isNotNull();
    }

    @Test
    void shouldCreateDateRangeWithSameStartAndEnd() {
        var startDate = LocalDate.now();
        var dateRange = new DateRange(startDate, startDate);

        assertThat(dateRange).isNotNull();
    }

    @Test
    void shouldNotCreateDateRangeWithNullDates() {
        assertThatThrownBy(() -> new DateRange(null, null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> new DateRange(LocalDate.now(), null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> new DateRange(null, LocalDate.now())).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldNotCreateDateRangeWithStartAfterEnd() {
        assertThatThrownBy(() -> new DateRange(LocalDate.now().plusDays(1), LocalDate.now())).isInstanceOf(IllegalArgumentException.class);
    }

    @Nested
    class Contains {
        @Test
        void shouldContainDate() {
            var startDate = LocalDate.now();
            var endDate = startDate.plusDays(7);
            var dateRange = new DateRange(startDate, endDate);

            var containedDate = LocalDate.now().plusDays(3);
            assertThat(dateRange.contains(containedDate)).isTrue();
        }

        @Test
        void shouldContainStartDate() {
            var startDate = LocalDate.now();
            var endDate = startDate.plusDays(7);
            var dateRange = new DateRange(startDate, endDate);

            assertThat(dateRange.contains(startDate)).isTrue();
        }

        @Test
        void shouldContainEndDate() {
            var startDate = LocalDate.now();
            var endDate = startDate.plusDays(7);
            var dateRange = new DateRange(startDate, endDate);

            assertThat(dateRange.contains(endDate)).isTrue();
        }

        @Test
        void shouldNotContainDateOutsideRange() {
            var startDate = LocalDate.now();
            var endDate = startDate.plusDays(7);
            var dateRange = new DateRange(startDate, endDate);

            assertThat(dateRange.contains(startDate.minusDays(1))).isFalse();
            assertThat(dateRange.contains(endDate.plusDays(1))).isFalse();
        }
    }

    @Nested
    class Overlaps {
        @Test
        void shouldOverlapWithSameRange() {
            var startDate = LocalDate.now();
            var endDate = startDate.plusDays(7);
            var dateRange = new DateRange(startDate, endDate);

            assertThat(dateRange.overlaps(dateRange)).isTrue();
        }

        @Test
        void shouldNotOverlapWithRangeAfter() {
            var startDate = LocalDate.now();
            var endDate = startDate.plusDays(7);
            var dateRange = new DateRange(startDate, endDate);

            var nonOverlappingRange = new DateRange(endDate.plusDays(1), endDate.plusDays(8));
            assertThat(dateRange.overlaps(nonOverlappingRange)).isFalse();
        }

        @Test
        void shouldNotOverlapWithRangeBefore() {
            var startDate = LocalDate.now();
            var endDate = startDate.plusDays(7);
            var dateRange = new DateRange(startDate, endDate);

            var nonOverlappingRange = new DateRange(startDate.minusDays(8), startDate.minusDays(1));
            assertThat(dateRange.overlaps(nonOverlappingRange)).isFalse();
        }

        @Test
        void shouldOverlapWithRangeInside() {
            var startDate = LocalDate.now();
            var endDate = startDate.plusDays(7);
            var dateRange = new DateRange(startDate, endDate);

            var insideRange = new DateRange(startDate.plusDays(1), endDate.minusDays(1));
            assertThat(dateRange.overlaps(insideRange)).isTrue();
        }

        @Test
        void shouldOverlapWithRangeThatFullyContains() {
            var startDate = LocalDate.now();
            var endDate = startDate.plusDays(7);
            var dateRange = new DateRange(startDate, endDate);

            var outerRange = new DateRange(startDate.minusDays(1), endDate.plusDays(1));
            assertThat(dateRange.overlaps(outerRange)).isTrue();
        }

        @Test
        void shouldOverlapWithRangeStartingOnSameDay() {
            var startDate = LocalDate.now();
            var endDate = startDate.plusDays(7);
            var dateRange = new DateRange(startDate, endDate);

            var sameDayRange = new DateRange(startDate, startDate.plusDays(1));
            assertThat(dateRange.overlaps(sameDayRange)).isTrue();
        }

        @Test
        void shouldOverlapWithRangeEndingOnSameDay() {
            var startDate = LocalDate.now();
            var endDate = startDate.plusDays(7);
            var dateRange = new DateRange(startDate, endDate);

            var sameDayRange = new DateRange(endDate.minusDays(2), endDate);
            assertThat(dateRange.overlaps(sameDayRange)).isTrue();
        }
    }
    @Nested
    class Duration {
        @ParameterizedTest
        @ValueSource(ints = {0, 1, 3, 30, 60, 125, 365, 366})
        void shouldCorrectNumberOfDays(int durationInDays) {
            var startDate = LocalDate.now();
            var endDate = startDate.plusDays(durationInDays);
            var dateRange = new DateRange(startDate, endDate);

            assertThat(dateRange.duration()).isEqualTo(Period.between(startDate, endDate));
        }
    }
}
