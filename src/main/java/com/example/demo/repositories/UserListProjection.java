package com.example.demo.repositories;

/**
 * Projection for the users list endpoint. Only id, name, and email are
 * selected from the database (no ratings), avoiding unnecessary joins and
 * reducing memory use.
 */
public interface UserListProjection {
    Long getId();
    String getName();
    String getEmail();
}
