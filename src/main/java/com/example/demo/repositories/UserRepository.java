package com.example.demo.repositories;

import com.example.demo.models.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    // Fetch user with ratings in a single query (prevents N+1 problem)
    @EntityGraph(attributePaths = { "ratings", "ratings.movie" })
    Optional<User> findById(Long id);
}
