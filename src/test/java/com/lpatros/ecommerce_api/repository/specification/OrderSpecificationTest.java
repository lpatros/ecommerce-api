package com.lpatros.ecommerce_api.repository.specification;

import com.lpatros.ecommerce_api.dto.order.OrderFilter;
import com.lpatros.ecommerce_api.entity.order.Order;
import com.lpatros.ecommerce_api.entity.order.OrderStatus;
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
class OrderSpecificationTest {

    @Mock private Root<Order> root;
    @Mock private CriteriaQuery<?> query;
    @Mock private CriteriaBuilder cb;
    @Mock private Root userRoot;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() {
        Predicate predicate = mock(Predicate.class);
        Path<Object> path = mock(Path.class);
        doReturn(predicate).when(cb).and(any(Predicate[].class));
        doReturn(predicate).when(cb).like(any(Expression.class), anyString());
        doReturn(predicate).when(cb).equal(any(), any());
        doReturn(mock(Expression.class)).when(cb).lower(any(Expression.class));
        doReturn(path).when(root).<Object>get("trackingCode");
        doReturn(path).when(root).<Object>get("status");
        doReturn(userRoot).when(root).<Object>get("user");
        doReturn(path).when(userRoot).<Object>get("id");
    }

    @Test
    void filter_appliesAllPredicates_whenAllFiltersSet() {
        OrderFilter filter = new OrderFilter(OrderStatus.PENDING, "TRACK123", 1L);

        Specification<Order> spec = OrderSpecification.filter(filter);
        spec.toPredicate(root, query, cb);

        verify(cb).equal(any(), eq("TRACK123"));
        verify(cb).like(any(Expression.class), eq("%pending%"));
        verify(cb).equal(any(), eq(1L));
    }

    @Test
    void filter_addsNoPredicates_whenFilterEmpty() {
        OrderFilter filter = new OrderFilter(null, "", null);

        Specification<Order> spec = OrderSpecification.filter(filter);
        spec.toPredicate(root, query, cb);

        verify(cb, never()).equal(any(), any());
        verify(cb, never()).like(any(), anyString());
    }

    @Test
    void filter_skipsIndividualPredicates_whenNull() {
        OrderFilter filter = new OrderFilter(null, null, null);

        Specification<Order> spec = OrderSpecification.filter(filter);
        spec.toPredicate(root, query, cb);

        verify(cb, never()).equal(any(), any());
        verify(cb, never()).like(any(), anyString());
    }
}
