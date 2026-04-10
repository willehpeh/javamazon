package tech.reactiv.ecommerce.catalog;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
abstract class AcceptanceTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", SharedPostgres.INSTANCE::getJdbcUrl);
        registry.add("spring.datasource.username", SharedPostgres.INSTANCE::getUsername);
        registry.add("spring.datasource.password", SharedPostgres.INSTANCE::getPassword);
    }

    @AfterEach
    void tearDown() {
        List<String> tables = jdbcTemplate.queryForList(
                "SELECT table_name FROM information_schema.tables " +
                        "WHERE table_schema = 'public' AND table_name != 'flyway_schema_history'",
                String.class
        );
        if (!tables.isEmpty()) {
            jdbcTemplate.execute("TRUNCATE " + String.join(", ", tables) + " CASCADE");
        }
    }
}
