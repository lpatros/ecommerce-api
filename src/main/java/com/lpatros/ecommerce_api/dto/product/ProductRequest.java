package com.lpatros.ecommerce_api.dto.product;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @NotBlank(message = "O nome do produto é obrigatório")
    private String name;

    private String description;

    @NotBlank(message = "O estoque do produto é obrigatório")
    private Long stock;

    @NotBlank(message = "O preço do produto é obrigatório")
    private BigDecimal price;

    @NotBlank(message = "A URL da imagem do produto é obrigatória")
    private String imageUrl;

    @NotBlank(message = "A categoria do produto é obrigatória")
    private Long categoryId;
}
