package tech.reactiv.ecommerce.catalog.product;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public final class TestProductView {

    private static ProductView productView(UUID id, String name, String description, int priceInCents, String category, boolean active) {
        return new ProductView(id, name, description, priceInCents, category, active);
    }

    public static ProductView basic(ProductId id) {
        var suffix = UUID.randomUUID().toString().substring(0, 8);
        return productView(id.value(),
                "Product " + suffix,
                "Description " + suffix,
                ThreadLocalRandom.current().nextInt(100, 10001),
                "Category " + suffix,
                true);
    }

    public static List<ProductView> basicList(int size) {
        return IntStream.range(0, size)
                .mapToObj(_ -> basic(ProductId.create()))
                .toList();
    }

    public static ProductView withCategory(ProductView productView, String category) {
        return productView(productView.id(), productView.name(), productView.description(), productView.priceInCents(), category, true);
    }
}
