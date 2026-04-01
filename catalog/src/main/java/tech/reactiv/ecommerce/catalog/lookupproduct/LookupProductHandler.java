package tech.reactiv.ecommerce.catalog.lookupproduct;

import tech.reactiv.ecommerce.catalog.product.views.ProductView;
import tech.reactiv.ecommerce.catalog.product.views.ProductViews;

import java.util.Optional;

public class LookupProductHandler {

    private final ProductViews views;

    public LookupProductHandler(ProductViews views) {
        this.views = views;
    }

    public Optional<ProductView> handle(LookupProductRequest request) {
        return views.withId(request.productId());
    }
}
