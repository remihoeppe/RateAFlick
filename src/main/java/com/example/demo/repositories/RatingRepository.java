package com.example.demo.repositories;

import com.example.demo.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByUser_IdAndMovie_Id(Long userId, Long movieId);
}
