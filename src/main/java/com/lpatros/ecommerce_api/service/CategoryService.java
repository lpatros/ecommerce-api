package com.lpatros.ecommerce_api.service;

import com.lpatros.ecommerce_api.dto.CategoryDTO;
import com.lpatros.ecommerce_api.entity.Category;
import com.lpatros.ecommerce_api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> find(String status) {
        try {

            Boolean statusBool = null;
            if (status != null) {
                if (status.equalsIgnoreCase("true")) {
                    statusBool = true;
                } else if (status.equalsIgnoreCase("false")) {
                    statusBool = false;
                } else {
                    throw new RuntimeException("Status inválido: " + status);
                }
            }

            List<Category> categories = categoryRepository.find(statusBool);

            return categories.stream()
                    .map(category -> new CategoryDTO(category.getName(), category.getStatus()))
                    .toList();

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar categorias com status: " + status);
        }
    }

    public CategoryDTO findById(Long id) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada com id: " + id));

            return new CategoryDTO(category.getName(), category.getStatus());
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

            return new CategoryDTO(
                    savedCategory.getName(),
                    savedCategory.getStatus());

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

            return new CategoryDTO(
                    updatedCategory.getName(),
                    updatedCategory.getStatus());

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
