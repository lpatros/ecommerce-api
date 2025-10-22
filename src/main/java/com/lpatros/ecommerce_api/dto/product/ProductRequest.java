package com.lpatros.ecommerce_api.dto.product;

import com.lpatros.ecommerce_api.dto.productImage.ProductImageRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @NotBlank(message = "O nome do produto é obrigatório")
    private String name;

    private String description;

    @NotNull(message = "A quantidade em estoque do produto é obrigatória")
    private Long stock;

    @NotNull(message = "O preço do produto é obrigatório")
    private BigDecimal price;

    @NotNull(message = "O produto deve conter ao menos uma imagem")
    private List<ProductImageRequest> productImages;

    @NotNull(message = "A categoria do produto é obrigatória")
    private Long categoryId;
}
