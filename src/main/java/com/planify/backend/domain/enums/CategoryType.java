package com.planify.backend.domain.enums;

public enum CategoryType {
    income,
    expense;

    public static CategoryType fromString(String value) {
        switch (value.toLowerCase()) {
            case "income":
                return income;
            case "expense":
                return expense;
            default:
                throw new IllegalArgumentException("Tipo de categoría inválido: " + value);
        }
    }
}
