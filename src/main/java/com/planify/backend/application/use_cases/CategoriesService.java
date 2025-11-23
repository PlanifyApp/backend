package com.planify.backend.application.use_cases;
import com.planify.backend.application.dtos.CategoryRequest;
import com.planify.backend.application.dtos.CategoryResponse;
import com.planify.backend.application.dtos.CategorySimpleResponse;
import com.planify.backend.domain.enums.CategoryType;
import com.planify.backend.domain.models.CategoryEntity;
import com.planify.backend.infrastructure.repositories.CategoriesRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;

    public CategoriesService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public Flux<CategoryResponse> getCategoriesByUserId(Integer userId) {
        return categoriesRepository.findByUserId(userId)
                .map(this::toResponse);
    }

    public Flux<CategorySimpleResponse> getCategoryNamesByUserId(Integer userId) {
        return categoriesRepository.findByUserId(userId)
                .map(entity -> new CategorySimpleResponse(entity.getId(), entity.getName()));
    }

    public Mono<CategoryResponse> createCategory(CategoryRequest request) {
        return categoriesRepository.insertCategory(
                request.getName(),
                request.getBudgeted(),
                request.getUserId(),
                request.getType().trim().toLowerCase()
        ).map(this::toResponse);
    }

    public Flux<CategoryResponse> getCategoriesByUserIdAndType(Integer userId, String type) {
        return categoriesRepository.findByUserIdAndType(userId, type)
                .map(this::toResponse);
    }

    public Mono<CategoryResponse> updateCategory(Long id, CategoryRequest request) {
        return categoriesRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Categoría no encontrada")))
                .flatMap(existing -> {
                    existing.setName(request.getName());
                    existing.setBudgeted(request.getBudgeted());
                    existing.setUserId(request.getUserId());
                    existing.setType(CategoryType.fromString(request.getType()));
                    return categoriesRepository.save(existing);
                })
                .map(this::toResponse);
    }

    public Mono<Void> deleteCategory(Long id) {
        return categoriesRepository.deleteById(id);
    }

    private CategoryResponse toResponse(CategoryEntity entity) {
        return new CategoryResponse(
                entity.getId(),
                entity.getName(),
                entity.getBudgeted()
        );
    }
}


