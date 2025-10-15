package com.lpatros.ecommerce_api.mapper;

import com.lpatros.ecommerce_api.dto.CategoryDTO;
import com.lpatros.ecommerce_api.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDTO(Category category);
}
