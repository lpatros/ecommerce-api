package com.lpatros.ecommerce_api.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    @NotBlank(message = "O nome da categoria é obrigatório")
    private String name;

    @NotNull(message = "O status da categoria é obrigatório")
    private Boolean status;
}
