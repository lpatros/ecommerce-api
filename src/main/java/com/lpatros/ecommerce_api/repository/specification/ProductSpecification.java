package com.lpatros.ecommerce_api.repository.specification;

import com.lpatros.ecommerce_api.dto.product.ProductFilter;
import com.lpatros.ecommerce_api.entity.Category;
import com.lpatros.ecommerce_api.entity.Product;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> filter(ProductFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getName() != null && !filter.getName().isEmpty()) {
                String namePattern = "%" + filter.getName().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("name")), namePattern));
            }

            if (filter.getMinStock() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("stock"), filter.getMinStock()));
            }

            if (filter.getMaxStock() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("stock"), filter.getMaxStock()));
            }

            if (filter.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));
            }

            if (filter.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));
            }

            if (filter.getCategoryId() != null) {
                Join<Product, Category> joinCategoria = root.join("category", JoinType.INNER);
                predicates.add(cb.equal(joinCategoria.get("id"), filter.getCategoryId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
