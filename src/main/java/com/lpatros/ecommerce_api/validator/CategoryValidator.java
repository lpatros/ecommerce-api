package com.lpatros.ecommerce_api.validator;

import com.lpatros.ecommerce_api.dto.category.CategoryRequest;
import com.lpatros.ecommerce_api.exception.NotUniqueException;
import com.lpatros.ecommerce_api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryValidator {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryValidator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void validateCreate(CategoryRequest categoryRequest) {
        validateNameUnique(categoryRequest.getName(), null);
    }

    public void validateUpdate(CategoryRequest categoryRequest, Long categoryId) {
        validateNameUnique(categoryRequest.getName(), categoryId);
    }

    private void validateNameUnique(String name, Long idToIgnore) {
        boolean exists;
        if (idToIgnore == null) {
            exists = categoryRepository.existsByName(name);
        } else {
            exists = categoryRepository.existsByNameAndIdNot(name, idToIgnore);
        }
        if (exists) {
            throw new NotUniqueException("Category", "name");
        }
    }
}
