package tech.reactiv.ecommerce.catalog.promotion;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tech.reactiv.ecommerce.catalog.category.CategoryId;
import tech.reactiv.ecommerce.catalog.product.ProductId;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PromotionTest {

    static Stream<PromotionTarget> targets() {
        return Stream.of(
                new AllProducts(),
                new ByCategory(new CategoryId(UUID.randomUUID())),
                new ByProducts(Set.of(ProductId.create(), ProductId.create(), ProductId.create()))
        );
    }

    @ParameterizedTest
    @MethodSource("targets")
    void shouldCreatePromotionWithTarget(PromotionTarget target) {
        var id = new PromotionId(UUID.randomUUID());
        var description = new PromotionDescription("Summer Sale");
        var discountPercent = new PromotionDiscountPercent(20);
        var startDate = LocalDate.of(2023, 6, 1);
        var endDate = LocalDate.of(2023, 8, 31);

        var promotion = new Promotion(id, description, discountPercent, startDate, endDate, target);

        assertThat(promotion.state()).isEqualTo(
                new Promotion.State(id.value(), description.value(), discountPercent.value(), startDate, endDate, target)
        );
    }

    @Test
    void shouldNotCreatePromotionIfEndDateIsBeforeStartDate() {
        assertThatThrownBy(() -> new Promotion(
                new PromotionId(UUID.randomUUID()),
                new PromotionDescription("Summer Sale"),
                new PromotionDiscountPercent(20),
                LocalDate.of(2023, 8, 31),
                LocalDate.of(2023, 6, 1),
                new AllProducts()
        )).isInstanceOf(IllegalArgumentException.class);
    }
}
