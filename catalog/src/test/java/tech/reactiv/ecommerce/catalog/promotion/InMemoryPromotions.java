package tech.reactiv.ecommerce.catalog.promotion;

import java.util.HashMap;
import java.util.Map;

public class InMemoryPromotions implements Promotions {
    public final Map<PromotionId, Promotion> list = new HashMap<>();

    @Override
    public void schedule(Promotion promotion) {
        list.put(promotion.id(), promotion);
    }
}
