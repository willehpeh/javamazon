package tech.reactiv.ecommerce.catalog.product;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public final class TestProductView {
    public static ProductView basic(ProductId id) {
        var suffix = UUID.randomUUID().toString().substring(0, 8);
        return new ProductView(id.value(),
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
}
