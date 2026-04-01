package tech.reactiv.ecommerce.catalog.search;

import tech.reactiv.ecommerce.catalog.product.views.ProductView;
import tech.reactiv.ecommerce.shared.mediator.Query;

import java.util.List;
import java.util.UUID;

public record SearchCatalogRequest(UUID categoryId) implements Query<List<ProductView>> {

    public SearchCatalogRequest() {
        this(null);
    }

    public SearchCatalogRequest withCategory(UUID categoryId) {
        return new SearchCatalogRequest(categoryId);
    }

    public boolean wantsAllProducts() {
        return categoryId == null;
    }
}
