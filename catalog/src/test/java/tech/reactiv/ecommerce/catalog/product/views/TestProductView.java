package tech.reactiv.ecommerce.catalog.product.views;

import tech.reactiv.ecommerce.catalog.product.ProductId;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public final class TestProductView {

    private static ProductView productView(UUID id, String name, String description, BigDecimal price, UUID categoryId, boolean active) {
        return new ProductView(id, name, description, price, categoryId, active);
    }

    public static ProductView basic(ProductId id) {
        return productView(id.value(),
                "Product " + UUID.randomUUID().toString().substring(0, 8),
                "Description " + UUID.randomUUID().toString().substring(0, 8),
                BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(100, 10001), 2),
                UUID.randomUUID(),
                true);
    }

    public static List<ProductView> basicList(int size) {
        return IntStream.range(0, size)
                .mapToObj(_ -> basic(ProductId.create()))
                .toList();
    }

    public static ProductView withCategory(ProductView productView, UUID categoryId) {
        return productView(productView.id(), productView.name(), productView.description(), productView.price(), categoryId, true);
    }
}
