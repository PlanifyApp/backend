package com.planify.backend.presentation.controllers;

import com.planify.backend.application.dtos.CategoryRequest;
import com.planify.backend.application.dtos.CategoryResponse;
import com.planify.backend.application.dtos.CategorySimpleResponse;
import com.planify.backend.application.dtos.TransactionCategoryAllDTO;
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

    @GetMapping("/user/{userId}/names")
    public Flux<CategorySimpleResponse> getCategoryNames(@PathVariable Integer userId) {
        return categoriesService.getCategoryNamesByUserId(userId);
    }

    @PutMapping("/{id}")
    public Mono<CategoryResponse> update(@PathVariable Long id, @RequestBody CategoryRequest request) {
        return categoriesService.updateCategory(id, request);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return categoriesService.deleteCategory(id);
    }

    @GetMapping("/user/{userId}/type")
    public Flux<CategoryResponse> getCategoriesByType(
            @PathVariable Integer userId,
            @RequestParam String type // income o expense
    ) {
        return categoriesService.getCategoriesByUserIdAndType(userId, type);
    }

    @GetMapping("/user/{userId}/transactions")
    public Flux<TransactionCategoryAllDTO> getCategoryTransactions(
            @PathVariable Integer userId,
            @RequestParam(required = false) String type,       // income / expense
            @RequestParam(required = false) String startDate,  // YYYY-MM-DD
            @RequestParam(required = false) String endDate
    ) {
        return categoriesService.getCategoryTransactions(userId, type, startDate, endDate);
    }

}
