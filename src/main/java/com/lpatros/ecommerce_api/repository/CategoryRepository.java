package com.lpatros.ecommerce_api.repository;

import com.lpatros.ecommerce_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    default List<Category> find(Boolean status) {

        if (status != null) {
            return findAll().stream()
                    .filter(category -> category.getStatus().equals(status))
                    .toList();
        }

        return findAll();
    }
}
