package tech.reactiv.ecommerce.catalog;

import org.flywaydb.core.Flyway;
import org.testcontainers.postgresql.PostgreSQLContainer;

class SharedPostgres {
    static final PostgreSQLContainer INSTANCE = new PostgreSQLContainer("postgres:17");

    static {
        INSTANCE.start();
        Flyway.configure()
                .dataSource(INSTANCE.getJdbcUrl(), INSTANCE.getUsername(), INSTANCE.getPassword())
                .load()
                .migrate();
    }
}
