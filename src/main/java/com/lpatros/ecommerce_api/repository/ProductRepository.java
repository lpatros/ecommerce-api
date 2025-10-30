package com.lpatros.ecommerce_api.repository;

import com.lpatros.ecommerce_api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    boolean existsByIdAndProductImages_Id(Long productId, Long productImagesId);
}
