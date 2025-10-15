package com.lpatros.ecommerce_api.repository;

import com.lpatros.ecommerce_api.entity.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @EntityGraph(attributePaths = "status")
    @Query("SELECT c FROM Category c WHERE c.status = :status")
    List<Category> findByStatus(Boolean status);
}
