package tech.reactiv.ecommerce.catalog.driver;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.client.RestTestClient;
import tech.reactiv.ecommerce.catalog.addproduct.AddProductCommand;
import tech.reactiv.ecommerce.catalog.product.views.ProductView;

import java.math.BigDecimal;
import java.util.UUID;

@Component
class HttpCatalogDriver implements CatalogDriver {

    private final RestTestClient restClient;
    private final JdbcTemplate jdbc;

    HttpCatalogDriver(RestTestClient restClient, JdbcTemplate jdbc) {
        this.restClient = restClient;
        this.jdbc = jdbc;
    }

    @Override
    public UUID createCategory(String name) {
        var id = UUID.randomUUID();
        jdbc.update("INSERT INTO category (id, name) VALUES (?, ?)", id, name);
        return id;
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
}
