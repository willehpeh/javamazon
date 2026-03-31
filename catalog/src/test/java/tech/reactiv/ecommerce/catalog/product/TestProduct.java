package tech.reactiv.ecommerce.catalog.product;

import tech.reactiv.ecommerce.catalog.category.CategoryId;
import tech.reactiv.ecommerce.common.Money;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class TestProduct {
    public static Product basic(ProductId id) {
        return new Product(id,
                new ProductName("Product " + UUID.randomUUID().toString().substring(0, 8)),
                new ProductDescription("Description " + UUID.randomUUID().toString().substring(0, 8)),
                new ProductPrice(randomPrice()),
                CategoryId.create());
    }

    private static Money randomPrice() {
        return new Money(BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(100, 10001), 2));
    }
}
