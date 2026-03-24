package tech.reactiv.ecommerce.catalog.product;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public final class TestProduct {
    public static Product basic(ProductId id) {
        var suffix = UUID.randomUUID().toString().substring(0, 8);
        return new Product(id,
                new ProductName("Product " + suffix),
                new ProductDescription("Description " + suffix),
                new ProductPrice(ThreadLocalRandom.current().nextInt(100, 10001)),
                new ProductCategory("Category " + suffix));
    }

    public static List<Product> basicList(int size) {
        return IntStream.range(0, size)
                .mapToObj(_ -> basic(ProductId.create()))
                .toList();
    }
}
