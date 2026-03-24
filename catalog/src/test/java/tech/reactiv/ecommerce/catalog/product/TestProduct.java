package tech.reactiv.ecommerce.catalog.product;

public final class TestProduct {
    public static Product basic(ProductId id) {
        return new Product(id, new ProductName("Product A"), new ProductDescription("Product A description"), new ProductPrice(100), new ProductCategory("Toys"));
    }
}
