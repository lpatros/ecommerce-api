package com.lpatros.ecommerce_api.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class Pagination<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;
    private int pageSize;
    private int pageNumber;

    public Pagination(Page<T> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.pageSize = page.getSize();
        this.pageNumber = page.getNumber();
    }

    public static <T> Pagination<T> toPagination(Page<T> page) {
        return new Pagination<>(page);
    }
}
