package tech.reactiv.ecommerce.catalog.driver;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.client.RestTestClient;
import tech.reactiv.ecommerce.catalog.addproduct.AddProductCommand;
import tech.reactiv.ecommerce.catalog.product.views.ProductView;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
class HttpCatalogDriver implements CatalogDriver {

    private final RestTestClient restClient;

    HttpCatalogDriver(RestTestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public UUID addProduct(String name, String description, String price, UUID categoryId) {
        var productId = UUID.randomUUID();
        var command = new AddProductCommand(productId, name, description, new BigDecimal(price), categoryId);
        restClient.post().uri("/catalog/products").body(command)
                .exchange()
                .expectStatus().isCreated();
        return productId;
    }

    @Override
    public ProductView lookupProduct(UUID productId) {
        return restClient.get().uri("/catalog/products/{id}", productId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductView.class)
                .returnResult()
                .getResponseBody();
    }

    @Override
    public void expectNoProduct(UUID productId) {
        restClient.get().uri("/catalog/products/{id}", productId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Override
    public List<ProductView> listProducts() {
        return restClient.get().uri("/catalog/products")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<ProductView>>() {})
                .returnResult()
                .getResponseBody();
    }
}
