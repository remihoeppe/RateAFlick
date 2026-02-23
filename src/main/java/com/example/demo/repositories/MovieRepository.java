package com.example.demo.repositories;

import com.example.demo.dto.movie.MovieDetailWithActorRow;
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
     * Single query for GET /movies/{id}: one row per actor (or one row with null actor if none).
     * Movie + director + COALESCE(avg rating, 0) + actor id/name. One round-trip.
     */
    @Query(value = "SELECT m.id AS id, m.title AS title, m.release_year AS releaseYear, m.language AS language, "
            + "a_dir.name AS directorName, "
            + "COALESCE((SELECT AVG(r.score) FROM ratings r WHERE r.movie_id = m.id), 0) AS ratings_avg, "
            + "a_act.id AS actorId, a_act.name AS actorName "
            + "FROM movies m "
            + "LEFT JOIN directors d ON d.artist_id = m.director_id "
            + "LEFT JOIN artists a_dir ON a_dir.id = d.artist_id "
            + "LEFT JOIN movie_actors ma ON ma.movie_id = m.id "
            + "LEFT JOIN artists a_act ON a_act.id = ma.actor_id "
            + "WHERE m.id = :id", nativeQuery = true)
    List<MovieDetailWithActorRow> findMovieDetailWithActorsById(@Param("id") Long id);

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
