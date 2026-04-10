package tech.reactiv.ecommerce.catalog.driver;

import tech.reactiv.ecommerce.catalog.product.views.ProductView;
import tech.reactiv.ecommerce.catalog.promotion.PromotionTarget;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CatalogDriver {
    UUID addProduct(String name, String description, String price, UUID categoryId);
    ProductView lookupProduct(UUID productId);
    void expectNoProduct(UUID productId);
    List<ProductView> listProducts();
    void schedulePromotion(String description, int discountPercent, LocalDate startDate, LocalDate endDate, PromotionTarget target);
}
