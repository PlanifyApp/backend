package com.planify.backend.presentation.controllers;

import com.planify.backend.application.dtos.CategoryRequest;
import com.planify.backend.application.dtos.CategoryResponse;
import com.planify.backend.application.use_cases.CategoriesService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    private final CategoriesService categoriesService;

    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping("/user/{userId}")
    public Flux<CategoryResponse> getByUserId(@PathVariable Integer userId) {
        return categoriesService.getCategoriesByUserId(userId);
    }

    @PostMapping
    public Mono<CategoryResponse> create(@RequestBody CategoryRequest request) {
        return categoriesService.createCategory(request);
    }

    @PutMapping("/{id}")
    public Mono<CategoryResponse> update(@PathVariable Long id, @RequestBody CategoryRequest request) {
        return categoriesService.updateCategory(id, request);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return categoriesService.deleteCategory(id);
    }
}
