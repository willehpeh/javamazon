package tech.reactiv.ecommerce.catalog.product.views;

import tech.reactiv.ecommerce.catalog.product.ProductId;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public final class TestProductView {

    private static ProductView productView(UUID id, String name, String description, BigDecimal price, String category, boolean active) {
        return new ProductView(id, name, description, price, category, active);
    }

    public static ProductView basic(ProductId id) {
        var suffix = UUID.randomUUID().toString().substring(0, 8);
        return productView(id.value(),
                "Product " + suffix,
                "Description " + suffix,
                BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(100, 10001), 2),
                "Category " + suffix,
                true);
    }

    public static List<ProductView> basicList(int size) {
        return IntStream.range(0, size)
                .mapToObj(_ -> basic(ProductId.create()))
                .toList();
    }

    public static ProductView withCategory(ProductView productView, String category) {
        return productView(productView.id(), productView.name(), productView.description(), productView.price(), category, true);
    }
}
