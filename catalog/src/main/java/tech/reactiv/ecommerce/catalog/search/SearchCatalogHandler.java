package tech.reactiv.ecommerce.catalog.search;

import tech.reactiv.ecommerce.catalog.category.CategoryId;
import tech.reactiv.ecommerce.catalog.product.views.ProductView;
import tech.reactiv.ecommerce.catalog.product.views.ProductViews;

import java.util.List;

public class SearchCatalogHandler {

    private final ProductViews views;

    public SearchCatalogHandler(ProductViews views) {
        this.views = views;
    }

    public List<ProductView> handle(SearchCatalogRequest request) {
        if (request.wantsAllProducts()) {
            return views.all();
        }
        return views.forCategory(new CategoryId(request.categoryId()));
    }
}
