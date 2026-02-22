package com.example.demo.repositories;

import com.example.demo.models.Director;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DirectorRepository extends JpaRepository<Director, Long> {

    /**
     * List page in one round-trip: id, name, and total count (COUNT(*) OVER()).
     * Row: [id, name, total]. Spring appends LIMIT/OFFSET via Pageable.
     */
    @Query(value = "SELECT d.artist_id AS id, a.name AS name, COUNT(*) OVER() AS total "
            + "FROM directors d INNER JOIN artists a ON d.artist_id = a.id ORDER BY d.artist_id "
            + "\n-- #pageable\n", nativeQuery = true)
    List<Object[]> findDirectorListPage(Pageable pageable);

    /**
     * Fetch director with movies in one query (for GET by ID).
     */
    @Query("SELECT DISTINCT d FROM Directors d LEFT JOIN FETCH d.movies WHERE d.id = :id")
    Optional<Director> findByIdWithMovies(@Param("id") Long id);
}
