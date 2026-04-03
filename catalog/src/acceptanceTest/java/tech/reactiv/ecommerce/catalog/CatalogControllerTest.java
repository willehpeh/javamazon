package tech.reactiv.ecommerce.catalog;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import tech.reactiv.ecommerce.catalog.addproduct.AddProductCommand;
import tech.reactiv.ecommerce.catalog.lookupproduct.LookupProductRequest;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureRestTestClient
class CatalogControllerTest {
    @Container
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:17");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private RestTestClient restClient;
    @Autowired
    private JdbcTemplate jdbc;


    private final UUID categoryId = UUID.randomUUID();

    @BeforeAll
    static void setUp() {
        Flyway flyway = Flyway.configure()
                .dataSource(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword())
                .load();
        flyway.migrate();
    }

    @BeforeEach
    void setUpEach() {
        jdbc.update("INSERT INTO category (id, name) VALUES (?, 'Electronics')", categoryId);
    }

    @Test
    void createsAndRetrievesProduct() {
        var productId = UUID.randomUUID();
        var command = new AddProductCommand(productId, "Product A", "Description A", new BigDecimal("1.00"), categoryId);
        restClient.post().uri("/catalog/products").body(command)
                .exchange()
                .expectStatus().isCreated();

        restClient.get().uri("catalog/products/{id}", productId)
                .exchange()
                .expectStatus().isOk();
    }
}
