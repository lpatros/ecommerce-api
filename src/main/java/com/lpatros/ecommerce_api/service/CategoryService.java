package com.lpatros.ecommerce_api.service;

import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.category.CategoryFilter;
import com.lpatros.ecommerce_api.dto.category.CategoryRequest;
import com.lpatros.ecommerce_api.dto.category.CategoryResponse;
import com.lpatros.ecommerce_api.entity.Category;
import com.lpatros.ecommerce_api.exception.NotFoundException;
import com.lpatros.ecommerce_api.mapper.CategoryMapper;
import com.lpatros.ecommerce_api.repository.CategoryRepository;
import com.lpatros.ecommerce_api.repository.specification.CategorySpecification;
import com.lpatros.ecommerce_api.validator.CategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryValidator categoryValidator;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryValidator categoryValidator, CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryValidator = categoryValidator;
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public Pagination<CategoryResponse> findAll(CategoryFilter categoryFilter, Pageable pageable) {

        Specification<Category> specification = CategorySpecification.filter(categoryFilter);

        Page<Category> categories = categoryRepository.findAll(specification, pageable);

        return categoryMapper.toResponsePagination(categories);
    }

    public CategoryResponse findById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category", "id"));

        return categoryMapper.toResponse(category);
    }

    public CategoryResponse create(CategoryRequest categoryRequest) {

            Category category = categoryMapper.toEntity(categoryRequest);

            categoryValidator.validateCreate(categoryRequest);

            return categoryMapper.toResponse(categoryRepository.save(category));
    }

    public CategoryResponse update(Long id, CategoryRequest categoryRequest) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category", "id"));

        categoryValidator.validateUpdate(categoryRequest, id);

        Category updatedCategory = categoryMapper.toEntity(categoryRequest);
        updatedCategory.setId(id);

        return categoryMapper.toResponse(categoryRepository.save(updatedCategory));
    }

    public void delete(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category", "id"));

        categoryRepository.deleteById(id);
    }
}
