package tech.reactiv.ecommerce.catalog.lookupproduct;

import tech.reactiv.ecommerce.catalog.product.ProductId;
import tech.reactiv.ecommerce.catalog.product.views.ProductView;
import tech.reactiv.ecommerce.shared.mediator.Query;

import java.util.Optional;

public record LookupProductRequest(ProductId productId) implements Query<Optional<ProductView>> {
}
