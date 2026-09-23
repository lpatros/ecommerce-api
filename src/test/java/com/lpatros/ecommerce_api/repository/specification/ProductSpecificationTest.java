package com.lpatros.ecommerce_api.repository.specification;

import com.lpatros.ecommerce_api.dto.product.ProductFilter;
import com.lpatros.ecommerce_api.entity.Category;
import com.lpatros.ecommerce_api.entity.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
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
class ProductSpecificationTest {

    @Mock private Root<Product> root;
    @Mock private CriteriaQuery<?> query;
    @Mock private CriteriaBuilder cb;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() {
        Predicate predicate = mock(Predicate.class);
        doReturn(predicate).when(cb).and(any(Predicate[].class));
        doReturn(predicate).when(cb).like(any(Expression.class), anyString());
        doReturn(mock(Expression.class)).when(cb).lower(any(Expression.class));
        doReturn(predicate).when(cb).greaterThanOrEqualTo(any(Expression.class), any(Comparable.class));
        doReturn(predicate).when(cb).lessThanOrEqualTo(any(Expression.class), any(Comparable.class));
        doReturn(predicate).when(cb).equal(any(), any());

        Path<Object> path = mock(Path.class);
        doReturn(path).when(root).<Object>get("name");
        doReturn(path).when(root).<Object>get("stock");
        doReturn(path).when(root).<Object>get("price");

        Join<Object, Object> join = mock(Join.class);
        doReturn(join).when(root).join(eq("category"), any(JoinType.class));
        doReturn(path).when(join).<Object>get("id");
    }

    @Test
    void filter_appliesAllPredicates_whenAllFiltersSet() {
        ProductFilter filter = new ProductFilter("widget", 5L, 50L, 1.0, 100.0, 1L);

        Specification<Product> spec = ProductSpecification.filter(filter);
        spec.toPredicate(root, query, cb);

        verify(cb).like(any(Expression.class), eq("%widget%"));
        verify(cb).greaterThanOrEqualTo(any(Expression.class), eq((Comparable) 5L));
        verify(cb).lessThanOrEqualTo(any(Expression.class), eq((Comparable) 50L));
        verify(cb).greaterThanOrEqualTo(any(Expression.class), eq((Comparable) 1.0));
        verify(cb).lessThanOrEqualTo(any(Expression.class), eq((Comparable) 100.0));
        verify(root).join(eq("category"), any(JoinType.class));
    }

    @Test
    void filter_addsNoPredicates_whenFilterEmpty() {
        ProductFilter filter = new ProductFilter();

        Specification<Product> spec = ProductSpecification.filter(filter);
        spec.toPredicate(root, query, cb);

        verify(cb, never()).like(any(), anyString());
        verify(root, never()).join(anyString(), any(JoinType.class));
        verify(cb, never()).greaterThanOrEqualTo(any(Expression.class), any(Comparable.class));
        verify(cb, never()).lessThanOrEqualTo(any(Expression.class), any(Comparable.class));
        verify(cb, never()).equal(any(), any());
    }

    @Test
    void filter_skipsIndividualPredicates_whenNull() {
        ProductFilter filter = new ProductFilter(null, null, null, null, null, null);

        Specification<Product> spec = ProductSpecification.filter(filter);
        spec.toPredicate(root, query, cb);

        verify(cb, never()).greaterThanOrEqualTo(any(Expression.class), any(Comparable.class));
        verify(cb, never()).lessThanOrEqualTo(any(Expression.class), any(Comparable.class));
        verify(cb, never()).equal(any(), any());
    }
}
