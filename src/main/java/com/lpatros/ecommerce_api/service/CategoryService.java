package com.lpatros.ecommerce_api.service;

import com.lpatros.ecommerce_api.dto.category.CategoryRequest;
import com.lpatros.ecommerce_api.dto.category.CategoryResponse;
import com.lpatros.ecommerce_api.entity.Category;
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
        try {

            List<Category>  categories = categoryRepository.findByStatus(status);
            return categories.stream().map(categoryMapper::toResponse).toList();

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar as categorias");
        }
    }

    public CategoryResponse findById(Long id) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada com id: " + id));

            return categoryMapper.toResponse(category);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar categoria com id: " + id);
        }
    }

    public CategoryResponse create(CategoryRequest categoryRequest) {
        try {
            Category category = categoryMapper.toEntity(categoryRequest);

            Category savedCategory = categoryRepository.save(category);

            return categoryMapper.toResponse(savedCategory);

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao criar categoria: " + e.getMessage());
        }
    }

    public CategoryResponse update(Long id, CategoryRequest categoryRequest) {
        try {

            if (categoryRequest.getName() == null || categoryRequest.getName().isEmpty()) {
                throw new RuntimeException("Nome da categoria não pode ser nulo ou vazio");
            }

            Optional<Category> category = categoryRepository.findById(id);

            if (category.isEmpty()) {
                throw new RuntimeException("Categoria não encontrada com id: " + id);
            }

            category.get().setName(categoryRequest.getName());
            category.get().setStatus(categoryRequest.getStatus());

            return categoryMapper.toResponse(categoryRepository.save(category.get()));

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao atualizar categoria com id: " + id);
        }
    }

    public void delete(Long id) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada com id: " + id));

            if (category == null) {
                throw new RuntimeException("Categoria não encontrada com id: " + id);
            }

            if (!category.getStatus()) {
                throw new RuntimeException("Categoria já está inativa com id: " + id);
            }

            categoryRepository.disable(id);

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao deletar categoria com id: " + id);
        }
    }
}
