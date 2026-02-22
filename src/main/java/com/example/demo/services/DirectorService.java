package com.example.demo.services;

import com.example.demo.dto.common.PageResponse;
import com.example.demo.dto.director.DirectorListResponse;
import com.example.demo.dto.director.DirectorMovieSummary;
import com.example.demo.dto.director.DirectorResponse;
import com.example.demo.models.Director;
import com.example.demo.repositories.DirectorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorService {
        private static final Logger logger = LoggerFactory.getLogger(DirectorService.class);

        private final DirectorRepository directorRepo;

        public DirectorService(DirectorRepository directorRepo) {
                this.directorRepo = directorRepo;
        }

        public PageResponse<DirectorListResponse> findAllDirectors(Pageable pageable) {
                logger.debug("Finding all directors with pagination: page={}, size={}",
                                pageable.getPageNumber(), pageable.getPageSize());
                List<Object[]> rows = directorRepo.findDirectorListPage(pageable);
                List<DirectorListResponse> content = rows.stream()
                                .map(row -> new DirectorListResponse(
                                                ((Number) row[0]).longValue(),
                                                (String) row[1]))
                                .toList();
                long total = rows.isEmpty()
                                ? (pageable.getPageNumber() == 0 ? 0 : directorRepo.count())
                                : ((Number) rows.get(0)[2]).longValue();
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

        public DirectorResponse findDirectorById(Long id) {
                Director director = directorRepo.findByIdWithMovies(id)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                String.format("Director with ID: %d, was not found", id)));
                List<DirectorMovieSummary> movies = director.getMovies() != null
                                ? director.getMovies().stream()
                                                .map(m -> new DirectorMovieSummary(m.getId(), m.getTitle()))
                                                .toList()
                                : List.of();
                return new DirectorResponse(director.getId(), director.getName(), movies);
        }
}
