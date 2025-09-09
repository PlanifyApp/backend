package com.planify.backend.domain.models;

import java.time.LocalDateTime;

public class VariableExpenses {

    private Integer id;
    private Integer userId;
    private String name;
    private LocalDateTime dateTime;
    private Integer budget;
    private Integer currentValue;
    private GoalType type;
    private Integer categoryId;
    private Integer accountId;

    public VariableExpenses() {}

    public VariableExpenses(Integer id, Integer userId, String name, LocalDateTime dateTime, Integer budget, Integer currentValue, GoalType type, Integer categoryId, Integer accountId) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.dateTime = dateTime;
        this.budget = budget;
        this.currentValue = currentValue;
        this.type = type;
        this.categoryId = categoryId;
        this.accountId = accountId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Integer getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Integer currentValue) {
        this.currentValue = currentValue;
    }

    public GoalType getType() {
        return type;
    }

    public void setType(GoalType type) {
        this.type = type;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}
