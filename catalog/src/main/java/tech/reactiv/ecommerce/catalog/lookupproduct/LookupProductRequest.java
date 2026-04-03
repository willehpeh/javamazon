package tech.reactiv.ecommerce.catalog.lookupproduct;

import tech.reactiv.ecommerce.catalog.product.views.ProductView;
import tech.reactiv.ecommerce.shared.mediator.Query;

import java.util.Optional;
import java.util.UUID;

public record LookupProductRequest(UUID productId) implements Query<Optional<ProductView>> {
}
