package tech.reactiv.ecommerce.catalog.lookupproduct;

import org.junit.jupiter.api.Test;
import tech.reactiv.ecommerce.catalog.category.CategoryId;
import tech.reactiv.ecommerce.catalog.product.ProductId;
import tech.reactiv.ecommerce.catalog.product.views.InMemoryProductViews;
import tech.reactiv.ecommerce.catalog.product.views.ProductView;
import tech.reactiv.ecommerce.catalog.product.views.TestProductView;
import tech.reactiv.ecommerce.catalog.promotion.AllProducts;
import tech.reactiv.ecommerce.catalog.promotion.ByCategory;
import tech.reactiv.ecommerce.catalog.promotion.ByProducts;
import tech.reactiv.ecommerce.catalog.promotion.PromotionDiscountPercent;
import tech.reactiv.ecommerce.catalog.promotion.views.PromotionView;
import tech.reactiv.ecommerce.common.DateRange;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class LookupProductTest {
    private final InMemoryProductViews views = new InMemoryProductViews();
    private final InMemoryPromotionViews promotions = new InMemoryPromotionViews();
    private final LookupProductHandler handler = new LookupProductHandler(views, promotions);

    @Test
    void shouldLookupProductWithUnchangedPriceWhenNoPromotions() {
        var productId = ProductId.create();
        var productView = TestProductView.basic(productId);
        views.list.put(productId, productView);

        Optional<ProductView> found = handler.handle(new LookupProductRequest(productId.value()));
        assertThat(found.orElseThrow()).isEqualTo(productView);
    }

    @Test
    void shouldNotFindNonExistentProduct() {
        Optional<ProductView> found = handler.handle(new LookupProductRequest(ProductId.create().value()));
        assertThat(found).isEmpty();
    }

    @Test
    void shouldApplyActivePromotionTargetedAtAllProducts() {
        var productId = ProductId.create();
        var productView = TestProductView.basic(productId);
        views.list.put(productId, productView);
        var promotion = new PromotionView(
                new PromotionDiscountPercent(50),
                new DateRange(LocalDate.now().minusDays(3), LocalDate.now().plusDays(3)),
                new AllProducts()
        );
        promotions.list.add(promotion);

        var found = handler.handle(new LookupProductRequest(productId.value()));
        assertThat(found.orElseThrow().price()).isEqualTo(productView.price().percent(50));
    }

    @Test
    void shouldApplyActivePromotionTargetedAtProductCategory() {
        var productId = ProductId.create();
        var productView = TestProductView.basic(productId);
        views.list.put(productId, productView);
        var promotion = new PromotionView(
                new PromotionDiscountPercent(50),
                new DateRange(LocalDate.now().minusDays(3), LocalDate.now().plusDays(3)),
                new ByCategory(new CategoryId(productView.categoryId()))
        );
        promotions.list.add(promotion);

        var found = handler.handle(new LookupProductRequest(productId.value()));
        assertThat(found.orElseThrow().price()).isEqualTo(productView.price().percent(50));
    }

    @Test
    void shouldApplyActivePromotionTargetedAtSpecificProduct() {
        var productId = ProductId.create();
        var productView = TestProductView.basic(productId);
        views.list.put(productId, productView);
        var promotion = new PromotionView(
                new PromotionDiscountPercent(50),
                new DateRange(LocalDate.now().minusDays(3), LocalDate.now().plusDays(3)),
                new ByProducts(Set.of(productId))
        );
        promotions.list.add(promotion);

        var found = handler.handle(new LookupProductRequest(productId.value()));
        assertThat(found.orElseThrow().price()).isEqualTo(productView.price().percent(50));
    }

    @Test
    void shouldNotApplyFuturePromotion() {
        var productId = ProductId.create();
        var productView = TestProductView.basic(productId);
        views.list.put(productId, productView);
        var promotion = new PromotionView(
                new PromotionDiscountPercent(50),
                new DateRange(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3)),
                new ByProducts(Set.of(productId))
        );
        promotions.list.add(promotion);

        var found = handler.handle(new LookupProductRequest(productId.value()));
        assertThat(found.orElseThrow().price()).isEqualTo(productView.price());
    }

    @Test
    void shouldNotApplyExpiredPromotion() {
        var productId = ProductId.create();
        var productView = TestProductView.basic(productId);
        views.list.put(productId, productView);
        var promotion = new PromotionView(
                new PromotionDiscountPercent(50),
                new DateRange(LocalDate.now().minusDays(3), LocalDate.now().minusDays(1)),
                new ByProducts(Set.of(productId, ProductId.create()))
        );
        promotions.list.add(promotion);

        var found = handler.handle(new LookupProductRequest(productId.value()));
        assertThat(found.orElseThrow().price()).isEqualTo(productView.price());
    }

    @Test
    void shouldNotApplyPromotionFromDifferentCategory() {
        var productId = ProductId.create();
        var productView = TestProductView.basic(productId);
        views.list.put(productId, productView);
        var promotion = new PromotionView(
                new PromotionDiscountPercent(50),
                new DateRange(LocalDate.now().minusDays(3), LocalDate.now().plusDays(1)),
                new ByCategory(CategoryId.create())
        );
        promotions.list.add(promotion);

        var found = handler.handle(new LookupProductRequest(productId.value()));
        assertThat(found.orElseThrow().price()).isEqualTo(productView.price());
    }

    @Test
    void shouldNotApplyPromotionForOtherProducts() {
        var productId = ProductId.create();
        var productView = TestProductView.basic(productId);
        views.list.put(productId, productView);
        var promotion = new PromotionView(
                new PromotionDiscountPercent(50),
                new DateRange(LocalDate.now().minusDays(3), LocalDate.now().plusDays(1)),
                new ByProducts(Set.of(ProductId.create(), ProductId.create()))
        );
        promotions.list.add(promotion);

        var found = handler.handle(new LookupProductRequest(productId.value()));
        assertThat(found.orElseThrow().price()).isEqualTo(productView.price());
    }

    @Test
    void shouldApplyBestPromotion() {
        var productId = ProductId.create();
        var productView = TestProductView.basic(productId);
        views.list.put(productId, productView);
        var worstPromotion = new PromotionView(
                new PromotionDiscountPercent(10),
                new DateRange(LocalDate.now().minusDays(3), LocalDate.now().plusDays(1)),
                new AllProducts()
        );
        var middlePromotion = new PromotionView(
                new PromotionDiscountPercent(30),
                new DateRange(LocalDate.now().minusDays(3), LocalDate.now().plusDays(1)),
                new ByProducts(Set.of(productId, ProductId.create()))
        );
        var bestPromotion = new PromotionView(
                new PromotionDiscountPercent(50),
                new DateRange(LocalDate.now().minusDays(3), LocalDate.now().plusDays(1)),
                new ByCategory(new CategoryId(productView.categoryId()))
        );
        promotions.list.add(worstPromotion);
        promotions.list.add(middlePromotion);
        promotions.list.add(bestPromotion);

        var found = handler.handle(new LookupProductRequest(productId.value()));
        assertThat(found.orElseThrow().price()).isEqualTo(productView.price().percent(50));

    }
}
