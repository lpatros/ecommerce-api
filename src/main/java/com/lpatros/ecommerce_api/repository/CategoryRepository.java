package com.lpatros.ecommerce_api.repository;

import com.lpatros.ecommerce_api.entity.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByStatus(Boolean status);

    @Modifying
    @Transactional
    @Query("UPDATE Category c SET c.status = false WHERE c.id = :id")
    void disable(Long id);
}
