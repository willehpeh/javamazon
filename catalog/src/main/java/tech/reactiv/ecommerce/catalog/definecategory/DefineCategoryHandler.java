package tech.reactiv.ecommerce.catalog.definecategory;

import tech.reactiv.ecommerce.catalog.category.Category;
import tech.reactiv.ecommerce.catalog.category.CategoryId;
import tech.reactiv.ecommerce.catalog.category.CategoryName;

public class DefineCategoryHandler {

    private final Categories categories;

    public DefineCategoryHandler(Categories categories) {
        this.categories = categories;
    }

    public void handle(DefineCategoryCommand command) {
        var id = new CategoryId(command.id());
        var name = new CategoryName(command.name());
        categories.define(new Category(id, name));
    }
}
