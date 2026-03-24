package tech.reactiv.ecommerce.catalog.search;

public record SearchCatalogRequest(String category) {

    public SearchCatalogRequest() {
        this(null);
    }

    public SearchCatalogRequest withCategory(String category) {
        return new SearchCatalogRequest(category);
    }

    public boolean wantsAllProducts() {
        return category == null;
    }
}
