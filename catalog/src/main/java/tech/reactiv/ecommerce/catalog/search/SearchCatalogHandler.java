package tech.reactiv.ecommerce.catalog.search;

import tech.reactiv.ecommerce.catalog.product.Product;
import tech.reactiv.ecommerce.catalog.product.Products;

import java.util.List;

public class SearchCatalogHandler {

    private final Products repository;

    public SearchCatalogHandler(Products repository) {
        this.repository = repository;
    }

    public List<Product> handle(SearchCatalogRequest request) {
        return repository.all();
    }
}
