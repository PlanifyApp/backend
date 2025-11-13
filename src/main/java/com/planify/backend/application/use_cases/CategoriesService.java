package com.planify.backend.application.use_cases;
import com.planify.backend.application.dtos.CategoryRequest;
import com.planify.backend.application.dtos.CategoryResponse;
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

    public Mono<CategoryResponse> createCategory(CategoryRequest request) {
        CategoryEntity entity = new CategoryEntity(
                (Long) null, // 👈 aclarar el tipo
                request.getName(),
                request.getBudgeted(),
                request.getPercentSpent(),
                request.getUserId(),
                CategoryType.valueOf(request.getType().trim().toLowerCase())
        );
        return categoriesRepository.save(entity)
                .map(this::toResponse);
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
                    existing.setPercentSpent(request.getPercentSpent());
                    existing.setUserId(request.getUserId());
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
                entity.getBudgeted(),
                entity.getPercentSpent(),
                entity.getUserId(),
                entity.getType().name()
        );
    }
}

