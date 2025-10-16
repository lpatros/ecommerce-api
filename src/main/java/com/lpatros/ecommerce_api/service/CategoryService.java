package com.lpatros.ecommerce_api.service;

import com.lpatros.ecommerce_api.dto.category.CategoryRequest;
import com.lpatros.ecommerce_api.dto.category.CategoryResponse;
import com.lpatros.ecommerce_api.entity.Category;
import com.lpatros.ecommerce_api.exception.NotActiveException;
import com.lpatros.ecommerce_api.exception.NotFoundException;
import com.lpatros.ecommerce_api.exception.NotUniqueException;
import com.lpatros.ecommerce_api.mapper.CategoryMapper;
import com.lpatros.ecommerce_api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryResponse> findByStatus(Boolean status) {

        List<Category>  categories = categoryRepository.findByStatus(status);
        return categories.stream().map(categoryMapper::toResponse).toList();
    }

    public CategoryResponse findById(Long id) {

        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            throw new NotFoundException("Categoria", "id");
        }

        return categoryMapper.toResponse(category.get());
    }

    public CategoryResponse create(CategoryRequest categoryRequest) {
            Category category = categoryMapper.toEntity(categoryRequest);

            List<Category> existingCategories = categoryRepository.findByName(categoryRequest.getName());

            if (!existingCategories.isEmpty()) {
                throw new NotUniqueException("Categoria", "nome");
            }

            Category savedCategory = categoryRepository.save(category);

            return categoryMapper.toResponse(savedCategory);
    }

    public CategoryResponse update(Long id, CategoryRequest categoryRequest) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            throw new NotFoundException("Categoria", "id");
        }

        List<Category> existingCategories = categoryRepository.findByName(categoryRequest.getName());

        if (!existingCategories.isEmpty() && !existingCategories.getFirst().getId().equals(id)) {
            throw new NotUniqueException("Categoria", "nome");
        }

        category.get().setName(categoryRequest.getName());
        category.get().setStatus(categoryRequest.getStatus());

        return categoryMapper.toResponse(categoryRepository.save(category.get()));
    }

    public void delete(Long id) {

        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            throw new NotFoundException("Categoria", "id");
        }

        if (!category.get().getStatus()) {
            throw new NotActiveException(category.get().getName());
        }

        categoryRepository.disable(id);

    }
}
