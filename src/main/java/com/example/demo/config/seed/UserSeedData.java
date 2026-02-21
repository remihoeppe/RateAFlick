package com.example.demo.config.seed;

import java.util.List;

/**
 * Static seed data for Users. Format: (name, email).
 */
public final class UserSeedData {

    private UserSeedData() {
    }

    public static final List<NameEmail> USERS = List.of(
            new NameEmail("John Doe", "john.doe@example.com"),
            new NameEmail("Jane Smith", "jane.smith@example.com"),
            new NameEmail("Bob Johnson", "bob.johnson@example.com"),
            new NameEmail("Alice Williams", "alice.williams@example.com"),
            new NameEmail("Charlie Brown", "charlie.brown@example.com"),
            new NameEmail("Diana Prince", "diana.prince@example.com"),
            new NameEmail("Eve Davis", "eve.davis@example.com"),
            new NameEmail("Frank Miller", "frank.miller@example.com")
    );

    public record NameEmail(String name, String email) {
    }
}
