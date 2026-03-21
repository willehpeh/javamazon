package tech.reactiv.ecommerce.catalog.product;

public class ProductPrice {

    private final int price;

    public ProductPrice(int price) {
        this.price = price;
    }

    public int value() {
        return price;
    }
}
