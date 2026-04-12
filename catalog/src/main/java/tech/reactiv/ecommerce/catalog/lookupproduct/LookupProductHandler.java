package tech.reactiv.ecommerce.catalog.lookupproduct;

import org.springframework.stereotype.Component;
import tech.reactiv.ecommerce.catalog.category.CategoryId;
import tech.reactiv.ecommerce.catalog.product.ProductId;
import tech.reactiv.ecommerce.catalog.product.views.ProductView;
import tech.reactiv.ecommerce.catalog.product.views.ProductViews;
import tech.reactiv.ecommerce.catalog.promotion.views.PromotionView;
import tech.reactiv.ecommerce.catalog.promotion.views.PromotionViews;
import tech.reactiv.ecommerce.shared.mediator.QueryHandler;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

@Component
public class LookupProductHandler implements QueryHandler<LookupProductRequest, Optional<ProductView>> {

    private final ProductViews views;
    private final PromotionViews promotions;
    private final Clock clock;

    public LookupProductHandler(ProductViews views,
                                PromotionViews promotions,
                                Clock clock) {
        this.views = views;
        this.promotions = promotions;
        this.clock = clock;
    }

    private static Predicate<PromotionView> promotionAppliesTo(ProductView product, Clock clock) {
        return promotion -> promotion.dateRange().contains(LocalDate.now(clock)) && promotion.promotionTarget().appliesTo(new ProductId(product.id()), new CategoryId(product.categoryId()));
    }

    private static Comparator<PromotionView> descendingDiscountPercent() {
        return Comparator.comparing((PromotionView p) -> p.discountPercent().value());
    }

    public Optional<ProductView> handle(LookupProductRequest request) {
        var id = new ProductId(request.productId());
        return views.withId(id)
                .filter(ProductView::active)
                .map(product -> bestPromotionFor(product)
                        .map(promotion -> product.withPrice(promotion.applyTo(product.price())))
                        .orElse(product)
                );
    }

    private Optional<PromotionView> bestPromotionFor(ProductView product) {
        return promotions.all().stream()
                .filter(promotionAppliesTo(product, clock))
                .max(descendingDiscountPercent());
    }
}
