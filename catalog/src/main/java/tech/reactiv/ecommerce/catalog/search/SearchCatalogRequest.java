package tech.reactiv.ecommerce.catalog.search;

import java.util.UUID;

public record SearchCatalogRequest(UUID categoryId) {

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
