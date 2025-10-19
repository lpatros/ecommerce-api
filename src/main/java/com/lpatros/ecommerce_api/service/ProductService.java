package com.lpatros.ecommerce_api.service;

import com.lpatros.ecommerce_api.dto.product.ProductFilter;
import com.lpatros.ecommerce_api.dto.product.ProductRequest;
import com.lpatros.ecommerce_api.dto.product.ProductResponse;
import com.lpatros.ecommerce_api.entity.Category;
import com.lpatros.ecommerce_api.entity.Product;
import com.lpatros.ecommerce_api.exception.NotActiveException;
import com.lpatros.ecommerce_api.exception.NotFoundException;
import com.lpatros.ecommerce_api.mapper.ProductMapper;
import com.lpatros.ecommerce_api.repository.CategoryRepository;
import com.lpatros.ecommerce_api.repository.ProductRepository;
import com.lpatros.ecommerce_api.repository.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    public List<ProductResponse> findAll(ProductFilter productFilter) {

        Specification<Product> specification = ProductSpecification.filter(productFilter);

        List<Product> products = productRepository.findAll(specification);

        return products.stream().map(productMapper::toResponse).toList();
    }

    public ProductResponse findById(Long id) {

        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new NotFoundException("Produto", "id");
        }

        return productMapper.toResponse(product.get());
    }

    public ProductResponse create(ProductRequest productRequest) {

        Optional<Category> category = categoryRepository.findById(productRequest.getCategoryId());

        if (category.isEmpty()) {
            throw new NotFoundException("Categoria", "id");
        }

        Product product = productMapper.toEntity(productRequest, category.get());

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }

    public ProductResponse update(Long id, ProductRequest productRequest) {

        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new NotFoundException("Produto", "id");
        }

        Optional<Category> category = categoryRepository.findById(productRequest.getCategoryId());

        if (category.isEmpty()) {
            throw new NotFoundException("Categoria", "id");
        }

        Product updatedProduct = productMapper.toEntity(productRequest, category.get());
        updatedProduct.setId(id);
        updatedProduct.setCreatedAt(product.get().getCreatedAt());

        return productMapper.toResponse(productRepository.save(updatedProduct));
    }

    public void delete(Long id) {

        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new NotFoundException("Produto", "id");
        }

        if (!product.get().getStatus()) {
            throw new NotActiveException(product.get().getName());
        }

        productRepository.disable(id);
    }
}
