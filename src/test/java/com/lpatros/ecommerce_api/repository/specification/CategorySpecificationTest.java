package com.lpatros.ecommerce_api.repository.specification;

import com.lpatros.ecommerce_api.dto.category.CategoryFilter;
import com.lpatros.ecommerce_api.entity.Category;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.data.jpa.domain.Specification;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CategorySpecificationTest {

    @Mock private Root<Category> root;
    @Mock private CriteriaQuery<?> query;
    @Mock private CriteriaBuilder cb;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() {
        Predicate predicate = mock(Predicate.class);
        doReturn(predicate).when(cb).and(any(Predicate[].class));
        doReturn(predicate).when(cb).like(any(Expression.class), anyString());
        doReturn(mock(Expression.class)).when(cb).lower(any(Expression.class));
        doReturn(mock(Path.class)).when(root).<Object>get("name");
    }

    @Test
    void filter_addsNamePredicate_whenNamePresent() {
        Specification<Category> spec = CategorySpecification.filter(new CategoryFilter("elec"));
        spec.toPredicate(root, query, cb);

        verify(cb).like(any(Expression.class), eq("%elec%"));
    }

    @Test
    void filter_addsNoNamePredicate_whenNameEmpty() {
        Specification<Category> spec = CategorySpecification.filter(new CategoryFilter(""));
        spec.toPredicate(root, query, cb);

        verify(cb, never()).like(any(), anyString());
    }

    @Test
    void filter_addsNoNamePredicate_whenNameNull() {
        Specification<Category> spec = CategorySpecification.filter(new CategoryFilter(null));
        Predicate result = spec.toPredicate(root, query, cb);

        org.assertj.core.api.Assertions.assertThat(result).isNotNull();
        verify(cb, never()).like(any(), anyString());
    }
}
