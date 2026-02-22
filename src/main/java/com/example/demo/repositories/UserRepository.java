package com.example.demo.repositories;

import com.example.demo.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    /**
     * List endpoint: only selects id, name, email (no ratings).
     */
    Page<UserListProjection> findAllBy(Pageable pageable);

    /**
     * Fetch user with ratings and movie in one go (prevents N+1 on GET by ID).
     * JOIN FETCH forces ratings and movie to load in the same query.
     */
    @Query("SELECT DISTINCT u FROM Users u "
            + "LEFT JOIN FETCH u.ratings r "
            + "LEFT JOIN FETCH r.movie "
            + "WHERE u.id = :id")
    Optional<User> findByIdWithRatings(@Param("id") Long id);
}
