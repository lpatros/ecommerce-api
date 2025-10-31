package com.lpatros.ecommerce_api.service;

import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.product.ProductFilter;
import com.lpatros.ecommerce_api.dto.product.ProductPatch;
import com.lpatros.ecommerce_api.dto.product.ProductRequest;
import com.lpatros.ecommerce_api.dto.product.ProductResponse;
import com.lpatros.ecommerce_api.entity.Category;
import com.lpatros.ecommerce_api.entity.Product;
import com.lpatros.ecommerce_api.exception.NotFoundException;
import com.lpatros.ecommerce_api.mapper.ProductMapper;
import com.lpatros.ecommerce_api.repository.CategoryRepository;
import com.lpatros.ecommerce_api.repository.ProductRepository;
import com.lpatros.ecommerce_api.repository.specification.ProductSpecification;
import com.lpatros.ecommerce_api.validator.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProductService {

    private final ProductValidator productValidator;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductValidator productValidator,ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productValidator = productValidator;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    public Pagination<ProductResponse> findAll(ProductFilter productFilter, Pageable pageable) {

        Specification<Product> specification = ProductSpecification.filter(productFilter);

        Page<Product> products = productRepository.findAll(specification, pageable);

        return productMapper.toResponsePagination(products);
    }

    public ProductResponse findById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product", "id"));

        return productMapper.toResponse(product);
    }

    public ProductResponse create(ProductRequest productRequest) {

        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category", "id"));

        productValidator.validateCreate(productRequest);

        Product product = productMapper.toEntity(productRequest, category);

        return productMapper.toResponse(productRepository.save(product));
    }

    public ProductResponse update(Long id, ProductRequest productRequest) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product", "id"));

        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category", "id"));

        productValidator.validateUpdate(productRequest, id);

        Product updatedProduct = productMapper.toEntity(productRequest, category);
        updatedProduct.setId(id);
        updatedProduct.setCreatedAt(product.getCreatedAt());

        return productMapper.toResponse(productRepository.save(updatedProduct));
    }

    public ProductResponse partialUpdate(Long id, ProductPatch productPatch) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product", "id"));

        if (productPatch.getCategoryId() != null) {
            Category category = categoryRepository.findById(productPatch.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category", "id"));
            product.setCategory(category);
        }

        productValidator.validatePatch(productPatch, id);

        productMapper.updateEntityFromPatch(product, productPatch);

        return productMapper.toResponse(productRepository.save(product));
    }

    public void delete(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product", "id"));

        productRepository.deleteById(id);
    }
}
