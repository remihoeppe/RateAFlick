package com.example.demo.dto.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    /** Build from content + total (single round-trip). Use when the repository returns list + total in one query. */
    public static <T> PageResponse<T> from(List<T> content, long total, Pageable pageable) {
        int size = pageable.getPageSize();
        int totalPages = size > 0 ? (int) Math.ceil((double) total / size) : 0;
        int number = pageable.getPageNumber();
        return new PageResponse<>(
                content,
                total,
                totalPages,
                number,
                size,
                number == 0,
                number >= totalPages - 1,
                content.size());
    }
}
