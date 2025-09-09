package com.planify.backend.domain.models;

public enum GoalType {

    AHORRO("ahorro"),
    DEUDA("deuda"),
    INVERSION("inversion"),
    OTRO("otro");

    private final String value;

    GoalType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static GoalType fromValue(String value) {
        for(GoalType type : values()){
            if(type.getValue().equalsIgnoreCase(value)){
                return type;
            }
        }
        throw new IllegalArgumentException("Valor desconocido: " + value);
    }

}
