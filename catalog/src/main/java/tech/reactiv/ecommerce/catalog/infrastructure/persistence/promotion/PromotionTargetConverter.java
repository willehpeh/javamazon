package tech.reactiv.ecommerce.catalog.infrastructure.persistence.promotion;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tech.reactiv.ecommerce.catalog.promotion.PromotionTarget;
import tools.jackson.databind.ObjectMapper;

@Converter
public class PromotionTargetConverter implements AttributeConverter<PromotionTarget, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(PromotionTarget target) {
        return objectMapper.writeValueAsString(target);
    }

    @Override
    public PromotionTarget convertToEntityAttribute(String json) {
        return objectMapper.readValue(json, PromotionTarget.class);
    }
}
