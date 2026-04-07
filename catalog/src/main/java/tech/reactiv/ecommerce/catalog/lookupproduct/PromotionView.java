package tech.reactiv.ecommerce.catalog.lookupproduct;

import tech.reactiv.ecommerce.catalog.promotion.PromotionDiscountPercent;
import tech.reactiv.ecommerce.catalog.promotion.PromotionTarget;
import tech.reactiv.ecommerce.common.DateRange;
import tech.reactiv.ecommerce.common.Money;

public record PromotionView(PromotionDiscountPercent discountPercent,
                            DateRange dateRange,
                            PromotionTarget promotionTarget) {
    public Money applyTo(Money money) {
        return money.percent(discountPercent.value());
    }
}
