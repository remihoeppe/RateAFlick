package com.example.demo.repositories;

import com.example.demo.models.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DirectorRepository extends JpaRepository<Director, Long> {

    /**
     * Fetch director with movies in one query (for GET by ID).
     */
    @Query("SELECT DISTINCT d FROM Directors d LEFT JOIN FETCH d.movies WHERE d.id = :id")
    Optional<Director> findByIdWithMovies(@Param("id") Long id);
}
