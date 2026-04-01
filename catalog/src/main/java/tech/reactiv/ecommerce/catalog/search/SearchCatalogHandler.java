package tech.reactiv.ecommerce.catalog.search;

import org.springframework.stereotype.Component;
import tech.reactiv.ecommerce.catalog.category.CategoryId;
import tech.reactiv.ecommerce.catalog.product.views.ProductView;
import tech.reactiv.ecommerce.catalog.product.views.ProductViews;
import tech.reactiv.ecommerce.shared.mediator.QueryHandler;

import java.util.List;

@Component
public class SearchCatalogHandler implements QueryHandler<SearchCatalogRequest, List<ProductView>> {

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
