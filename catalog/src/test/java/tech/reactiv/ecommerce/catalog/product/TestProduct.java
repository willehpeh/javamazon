package tech.reactiv.ecommerce.catalog.product;

import org.jspecify.annotations.NonNull;
import tech.reactiv.ecommerce.common.Money;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class TestProduct {
    public static Product basic(ProductId id) {
        var suffix = UUID.randomUUID().toString().substring(0, 8);
        return new Product(id,
                new ProductName("Product " + suffix),
                new ProductDescription("Description " + suffix),
                new ProductPrice(randomPrice()),
                new ProductCategory("Category " + suffix));
    }

    private static Money randomPrice() {
        return new Money(BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(100, 10001), 2));
    }
}
