package tech.reactiv.ecommerce.catalog.product;

public class InMemoryProductsTest extends ProductsContractTest {

    @Override
    protected Products createProducts() {
        return new InMemoryProducts();
    }
}
