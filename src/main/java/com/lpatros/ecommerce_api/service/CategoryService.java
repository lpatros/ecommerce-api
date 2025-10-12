package com.lpatros.ecommerce_api.service;

import com.lpatros.ecommerce_api.dto.CategoryDTO;
import com.lpatros.ecommerce_api.entity.Category;
import com.lpatros.ecommerce_api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO getCategoryById(UUID id) {
        try {
            Category category = categoryRepository.findCategoryById(id)
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada com id: " + id));

            return new CategoryDTO(category.getName(), category.getStatus());
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar categoria com id: " + id);
        }
    }
}
