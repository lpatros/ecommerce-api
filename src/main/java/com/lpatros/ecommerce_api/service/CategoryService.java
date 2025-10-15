package com.lpatros.ecommerce_api.service;

import com.lpatros.ecommerce_api.dto.CategoryDTO;
import com.lpatros.ecommerce_api.entity.Category;
import com.lpatros.ecommerce_api.mapper.CategoryMapper;
import com.lpatros.ecommerce_api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryDTO> findAllByStatus(Boolean status) {
        try {

            if (status == null) {
                List<Category> categories = categoryRepository.findAll();
                return categories.stream().map(categoryMapper::toDTO).toList();
            }

            List<Category>  categories = categoryRepository.findByStatus(status);
            return categories.stream().map(categoryMapper::toDTO).toList();

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar as categorias");
        }
    }

    public CategoryDTO findById(Long id) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada com id: " + id));

            return categoryMapper.toDTO(category);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar categoria com id: " + id);
        }
    }

    public CategoryDTO create(CategoryDTO categoryDTO) {
        try {
            Category category = new Category();
            category.setName(categoryDTO.getName());
            category.setStatus(categoryDTO.getStatus());

            Category savedCategory = categoryRepository.save(category);

            return categoryMapper.toDTO(savedCategory);

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao criar categoria: " + e.getMessage());
        }
    }

    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada com id: " + id));

            category.setName(categoryDTO.getName());
            category.setStatus(categoryDTO.getStatus());
            Category updatedCategory = categoryRepository.save(category);

            return categoryMapper.toDTO(updatedCategory);

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao atualizar categoria com id: " + id);
        }
    }

    public void delete(Long id) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada com id: " + id));

            categoryRepository.delete(category);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao deletar categoria com id: " + id);
        }
    }
}
