package tech.reactiv.ecommerce.catalog.lookupproduct;

import org.springframework.stereotype.Component;
import tech.reactiv.ecommerce.catalog.product.ProductId;
import tech.reactiv.ecommerce.catalog.product.views.ProductView;
import tech.reactiv.ecommerce.catalog.product.views.ProductViews;
import tech.reactiv.ecommerce.shared.mediator.QueryHandler;

import java.util.Optional;

@Component
public class LookupProductHandler implements QueryHandler<LookupProductRequest, Optional<ProductView>> {

    private final ProductViews views;

    public LookupProductHandler(ProductViews views) {
        this.views = views;
    }

    public Optional<ProductView> handle(LookupProductRequest request) {
        return views.withId(new ProductId(request.productId()));
    }
}
