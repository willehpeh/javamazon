package tech.reactiv.ecommerce.catalog.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public abstract class ProductsContractTest {

    protected abstract Products createProducts();

    Products products;

    @BeforeEach
    void setUp() {
        products = createProducts();
    }

    @Test
    void shouldRetrieveAddedProduct() {
        var id = ProductId.create();
        var product = TestProduct.basic(id);

        products.add(product);

        var retrieved = products.productWithId(id);
        assertThat(retrieved).isPresent();
        assertThat(retrieved.get().state()).isEqualTo(product.state());
    }

    @Test
    void shouldExistAfterAdd() {
        var id = ProductId.create();
        products.add(TestProduct.basic(id));

        assertThat(products.contains(id)).isTrue();
    }

    @Test
    void shouldNotExistForUnknownId() {
        assertThat(products.contains(ProductId.create())).isFalse();
    }

    @Test
    void shouldReturnEmptyForUnknownId() {
        assertThat(products.productWithId(ProductId.create())).isEmpty();
    }

    @Test
    void shouldReflectModification() {
        var id = ProductId.create();
        var product = TestProduct.basic(id);
        products.add(product);

        product.reprice(new ProductPrice(new tech.reactiv.ecommerce.common.Money("99.99")));
        products.modify(product);

        var retrieved = products.productWithId(id);
        assertThat(retrieved).isPresent();
        assertThat(retrieved.get().state().price())
                .isEqualTo(new tech.reactiv.ecommerce.common.Money("99.99"));
    }

    @Test
    void shouldStillExistAfterModification() {
        var id = ProductId.create();
        var product = TestProduct.basic(id);
        products.add(product);

        product.reprice(new ProductPrice(new tech.reactiv.ecommerce.common.Money("50.00")));
        products.modify(product);

        assertThat(products.contains(id)).isTrue();
    }

    @Test
    void shouldRejectDuplicateAdd() {
        var id = ProductId.create();
        products.add(TestProduct.basic(id));

        assertThatThrownBy(() -> products.add(TestProduct.basic(id)))
                .isInstanceOf(IllegalStateException.class);
    }
}
