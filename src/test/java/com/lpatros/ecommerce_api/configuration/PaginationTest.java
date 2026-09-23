package com.lpatros.ecommerce_api.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PaginationTest {

    @Test
    void constructor_mapsPageFields() {
        var page = new PageImpl<>(List.of("a", "b"), PageRequest.of(1, 2), 10);
        Pagination<String> pagination = new Pagination<>(page);
        assertThat(pagination.getContent()).containsExactly("a", "b");
        assertThat(pagination.getTotalPages()).isEqualTo(5);
        assertThat(pagination.getTotalElements()).isEqualTo(10);
        assertThat(pagination.getPageSize()).isEqualTo(2);
        assertThat(pagination.getPageNumber()).isEqualTo(1);
    }

    @Test
    void toPagination_delegatesToConstructor() {
        var page = new PageImpl<>(List.of("x"), PageRequest.of(0, 10), 1);
        Pagination<String> pagination = Pagination.toPagination(page);
        assertThat(pagination.getContent()).containsExactly("x");
        assertThat(pagination.getPageNumber()).isZero();
    }
}
