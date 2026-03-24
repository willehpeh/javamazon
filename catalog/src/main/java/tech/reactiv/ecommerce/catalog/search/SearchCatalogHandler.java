package tech.reactiv.ecommerce.catalog.search;

import tech.reactiv.ecommerce.catalog.product.ProductView;
import tech.reactiv.ecommerce.catalog.product.ProductViews;

import java.util.List;

public class SearchCatalogHandler {

    private final ProductViews views;

    public SearchCatalogHandler(ProductViews views) {
        this.views = views;
    }

    public List<ProductView> handle(SearchCatalogRequest request) {
        var allProducts = views.all();
        if (request.wantsAllProducts()) {
            return allProducts;
        }
        return allProducts.stream().filter(product -> product.category().equals(request.category())).toList();
    }
}
