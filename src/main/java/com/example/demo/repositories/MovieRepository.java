package com.example.demo.repositories;

import com.example.demo.models.Movie;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    /**
     * Fetches movies by IDs with ratings and director loaded in one query (avoids
     * N+1).
     * Order by id to match default page sort.
     */
    @EntityGraph(attributePaths = { "ratings", "director" })
    @Query("SELECT m FROM Movies m WHERE m.id IN :ids ORDER BY m.id")
    List<Movie> findAllByIdInWithRatingsAndDirector(@Param("ids") List<Long> ids);
}
