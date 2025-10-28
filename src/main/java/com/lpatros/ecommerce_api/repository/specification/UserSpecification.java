package com.lpatros.ecommerce_api.repository.specification;

import com.lpatros.ecommerce_api.dto.user.UserFilter;
import com.lpatros.ecommerce_api.entity.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> filter(UserFilter userFilter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (userFilter.getCpf() != null && !userFilter.getCpf().isEmpty()) {
                predicates.add(cb.equal(root.get("cpf"), userFilter.getCpf()));
            }

            if (userFilter.getName() != null && !userFilter.getName().isEmpty()) {
                String namePattern = "%" + userFilter.getName().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("name")), namePattern));
            }

            if (userFilter.getPhoneNumber() != null && !userFilter.getPhoneNumber().isEmpty()) {
                predicates.add(cb.equal(root.get("phoneNumber"), userFilter.getPhoneNumber()));
            }

            if (userFilter.getEmail() != null && !userFilter.getEmail().isEmpty()) {
                predicates.add(cb.equal(root.get("email"), userFilter.getEmail()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
