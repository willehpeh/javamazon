package tech.reactiv.ecommerce.catalog;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureRestTestClient
abstract class AcceptanceTest {
    @Container
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:17");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        addDbContainerPropsToRegistry(registry);
    }

    private static void addDbContainerPropsToRegistry(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeAll
    static void setUp() {
        configureFlywayAndRunMigrations();
    }

    private static void configureFlywayAndRunMigrations() {
        Flyway.configure()
                .dataSource(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword())
                .load()
                .migrate();
    }
}
