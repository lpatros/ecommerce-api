package com.lpatros.ecommerce_api.repository;

import com.lpatros.ecommerce_api.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.deleted = true WHERE p.id = :id")
    void disable(Long id);
}
