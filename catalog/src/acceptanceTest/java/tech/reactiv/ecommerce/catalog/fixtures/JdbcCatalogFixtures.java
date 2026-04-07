package tech.reactiv.ecommerce.catalog.fixtures;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import tech.reactiv.ecommerce.catalog.promotion.PromotionTarget;

import java.time.LocalDate;
import java.util.UUID;

@Component
class JdbcCatalogFixtures implements CatalogFixtures {

    private final JdbcTemplate jdbc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    JdbcCatalogFixtures(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public UUID createCategory(String name) {
        var id = UUID.randomUUID();
        jdbc.update("INSERT INTO category (id, name) VALUES (?, ?)", id, name);
        return id;
    }

    @Override
    public UUID schedulePromotion(String description, int discountPercent, LocalDate startDate, LocalDate endDate, PromotionTarget target) {
        var id = UUID.randomUUID();
        jdbc.update(
                "INSERT INTO promotion (id, description, discount_percentage, start_date, end_date, target_data) VALUES (?, ?, ?, ?, ?, ?::jsonb)",
                id, description, discountPercent, startDate, endDate, toJson(target)
        );
        return id;
    }

    private String toJson(PromotionTarget target) {
        try {
            return objectMapper.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
