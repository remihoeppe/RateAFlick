package com.example.demo.repositories;

import com.example.demo.models.Movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByTitleAndReleaseYear(String title, int releaseYear);

    /**
     * Fetches a single movie by ID with director and actors loaded (for GET /movies/{id}).
     */
    @EntityGraph(attributePaths = { "director", "actors" })
    @Query("SELECT m FROM Movies m WHERE m.id = :id")
    Optional<Movie> findByIdWithDirectorAndActors(@Param("id") Long id);

    /**
     * Fetches movies by IDs with ratings and director loaded in one query (avoids
     * N+1).
     * Order by id to match default page sort.
     */
    @EntityGraph(attributePaths = { "ratings", "director" })
    @Query("SELECT m FROM Movies m WHERE m.id IN :ids ORDER BY m.id")
    List<Movie> findAllByIdInWithRatingsAndDirector(@Param("ids") List<Long> ids);

    /**
     * Fetches movies by IDs with director only (no ratings). Use for list endpoint.
     */
    @EntityGraph(attributePaths = { "director" })
    @Query("SELECT m FROM Movies m WHERE m.id IN :ids ORDER BY m.id")
    List<Movie> findAllByIdInWithDirector(@Param("ids") List<Long> ids);

    /**
     * Single query for list page: movie fields + director name + ratings avg.
     * Row: [id, title, release_year, language, director_name, ratings_avg].
     */
    @Query(value = "SELECT m.id, m.title, m.release_year, m.language, a.name AS director_name, "
            + "COALESCE((SELECT AVG(r.score) FROM ratings r WHERE r.movie_id = m.id), 0) AS ratings_avg "
            + "FROM movies m "
            + "LEFT JOIN directors d ON d.artist_id = m.director_id "
            + "LEFT JOIN artists a ON a.id = d.artist_id "
            + "ORDER BY m.id", countQuery = "SELECT COUNT(*) FROM movies m", nativeQuery = true)
    Page<Object[]> findMovieListPage(Pageable pageable);
}
