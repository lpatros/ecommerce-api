package com.lpatros.ecommerce_api.repository;

import com.lpatros.ecommerce_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
