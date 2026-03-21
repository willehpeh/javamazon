package tech.reactiv.ecommerce.catalog.product;

public class ProductPrice {

    private final int price;

    public ProductPrice(int price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.price = price;
    }

    public int value() {
        return price;
    }
}
