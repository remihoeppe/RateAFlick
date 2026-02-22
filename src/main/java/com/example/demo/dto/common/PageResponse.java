package com.example.demo.dto.common;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Stable DTO for paginated API responses. Use this instead of returning
 * {@link org.springframework.data.domain.Page} (e.g. PageImpl) directly,
 * which is not guaranteed to have a stable JSON structure.
 *
 * @param <T> type of each item in the page
 */
public record PageResponse<T>(
        List<T> content,
        long totalElements,
        int totalPages,
        int number,
        int size,
        boolean first,
        boolean last,
        int numberOfElements) {
    public static <T> PageResponse<T> of(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize(),
                page.isFirst(),
                page.isLast(),
                page.getNumberOfElements());
    }
}
