package com.planify.backend.infrastructure.converters;

import com.planify.backend.domain.models.FixedExpenseEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@ReadingConverter
@Component
public class GoalTypeReadConverter implements Converter<String, FixedExpenseEntity.GoalType> {

    @Override
    public FixedExpenseEntity.GoalType convert(String source) {
        // Convierte el texto de Postgres ENUM al enum de Java
        return FixedExpenseEntity.GoalType.valueOf(source.toUpperCase());
    }
}
