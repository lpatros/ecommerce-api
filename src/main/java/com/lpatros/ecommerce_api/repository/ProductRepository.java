package com.lpatros.ecommerce_api.repository;

import com.lpatros.ecommerce_api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
