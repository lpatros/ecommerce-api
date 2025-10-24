package com.lpatros.ecommerce_api.repository.specification;


import com.lpatros.ecommerce_api.dto.category.CategoryFilter;
import com.lpatros.ecommerce_api.entity.Category;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class CategorySpecification {

    public static Specification<Category> filter(CategoryFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getName() != null && !filter.getName().isEmpty()) {
                String namePattern = "%" + filter.getName().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("name")), namePattern));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Category> isNotDeleted() {
        return (root, query, cb) -> cb.isFalse(root.get("deleted"));
    }
}
