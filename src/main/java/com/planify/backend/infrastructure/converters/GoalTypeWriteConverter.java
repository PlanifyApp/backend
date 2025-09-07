package com.planify.backend.infrastructure.converters;

import com.planify.backend.domain.models.FixedExpenseEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@WritingConverter
@Component
public class GoalTypeWriteConverter implements Converter<FixedExpenseEntity.GoalType, String> {

    @Override
    public String convert(FixedExpenseEntity.GoalType source) {
        // Convierte el enum de Java a texto para Postgres ENUM
        return source.name().toLowerCase(); // Ej: AHORRO → "ahorro"
    }
}
