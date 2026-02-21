package com.example.demo.repositories;

import com.example.demo.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByUser_IdAndMovie_Id(Long userId, Long movieId);

    /** Returns [movieId, avgScore] per movie for the given movie IDs. */
    @Query("SELECT r.movie.id, AVG(r.score) FROM Ratings r WHERE r.movie.id IN :movieIds GROUP BY r.movie.id")
    List<Object[]> findAverageScoreByMovieIdIn(@Param("movieIds") List<Long> movieIds);
}
