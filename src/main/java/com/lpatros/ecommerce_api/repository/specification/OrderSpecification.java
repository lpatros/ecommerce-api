package com.lpatros.ecommerce_api.repository.specification;

import com.lpatros.ecommerce_api.dto.order.OrderFilter;
import com.lpatros.ecommerce_api.entity.order.Order;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OrderSpecification {

    public static Specification<Order> filter(OrderFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getTrackingCode() != null && !filter.getTrackingCode().isEmpty()) {
                String trackingCodePattern = "%" + filter.getTrackingCode().toLowerCase() + "%";
                predicates.add(cb.equal(root.get("trackingCode"), trackingCodePattern));
            }

            if (filter.getStatus() != null) {
                String statusPattern = "%" + filter.getStatus().toString().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("status")), statusPattern));
            }

            if (filter.getUserId() != null) {
                predicates.add(cb.equal(root.get("user").get("id"), filter.getUserId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
