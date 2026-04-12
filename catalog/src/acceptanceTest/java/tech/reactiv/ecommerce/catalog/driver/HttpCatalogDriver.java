package tech.reactiv.ecommerce.catalog.driver;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.client.RestTestClient;
import tech.reactiv.ecommerce.catalog.product.ProductId;
import tech.reactiv.ecommerce.catalog.product.views.ProductView;
import tech.reactiv.ecommerce.catalog.promotion.AllProducts;
import tech.reactiv.ecommerce.catalog.promotion.ByCategory;
import tech.reactiv.ecommerce.catalog.promotion.ByProducts;
import tech.reactiv.ecommerce.catalog.promotion.PromotionTarget;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
class HttpCatalogDriver implements CatalogDriver {

    private final RestTestClient restClient;

    HttpCatalogDriver(RestTestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public UUID addProduct(String name, String description, String price, UUID categoryId) {
        var productId = UUID.randomUUID();
        restClient.post().uri("/catalog/products").body(Map.of(
                        "productId", productId,
                        "productName", name,
                        "description", description,
                        "price", price,
                        "categoryId", categoryId))
                .exchange()
                .expectStatus().isCreated();
        return productId;
    }

    @Override
    public ProductView lookupProduct(UUID productId) {
        return restClient.get().uri("/catalog/products/{id}", productId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductView.class)
                .returnResult()
                .getResponseBody();
    }

    @Override
    public void expectNoProduct(UUID productId) {
        restClient.get().uri("/catalog/products/{id}", productId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Override
    public List<ProductView> listProducts() {
        return restClient.get().uri("/catalog/products")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<ProductView>>() {})
                .returnResult()
                .getResponseBody();
    }

    @Override
    public void schedulePromotion(String description, int discountPercent, LocalDate startDate, LocalDate endDate, PromotionTarget target) {
        restClient.post().uri("/catalog/promotions").body(Map.of(
                        "promotionId", UUID.randomUUID(),
                        "description", description,
                        "discountPercent", discountPercent,
                        "startDate", startDate,
                        "endDate", endDate,
                        "target", toTargetMap(target)))
                .exchange()
                .expectStatus().isCreated();
    }

    private Map<String, Object> toTargetMap(PromotionTarget target) {
        return switch (target) {
            case AllProducts _ -> Map.of("type", "ALL_PRODUCTS");
            case ByCategory byCategory -> Map.of("type", "BY_CATEGORY", "categoryId", byCategory.categoryId().value());
            case ByProducts byProducts -> Map.of("type", "BY_PRODUCTS", "productIds", byProducts.productIds().stream().map(ProductId::value).toList());
        };
    }

    @Override
    public void discontinueProduct(UUID productId) {
        restClient.post().uri("/catalog/products/{id}/discontinue", productId)
                .exchange()
                .expectStatus().isOk();
    }
}
