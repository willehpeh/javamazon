package tech.reactiv.ecommerce.catalog.lookupproduct;

import java.util.ArrayList;
import java.util.List;

public class InMemoryPromotionViews implements PromotionViews {

    List<PromotionView> list = new ArrayList<>();

    @Override
    public List<PromotionView> all() {
        return list;
    }
}
