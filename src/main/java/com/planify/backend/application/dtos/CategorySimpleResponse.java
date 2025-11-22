package com.planify.backend.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategorySimpleResponse {
    private Long id;
    private String name;
}
