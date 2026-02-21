package com.example.demo.config.seed;

import java.util.List;

/**
 * Static seed data for Ratings. Each entry is (userIndex, movieIndex, score).
 * Indices refer to the order of users and movies in UserSeedData and MovieSeedData.
 */
public final class RatingSeedData {

    private RatingSeedData() {
    }

    /** Format: { userIndex, movieIndex, score } */
    public static final List<int[]> RATINGS = List.of(
            // User 0 (John Doe)
            new int[]{0, 0, 9},   // The Matrix
            new int[]{0, 1, 10},  // Inception
            new int[]{0, 4, 9},   // The Dark Knight
            new int[]{0, 7, 10},  // The Godfather
            // User 1 (Jane Smith)
            new int[]{1, 0, 8},   // The Matrix
            new int[]{1, 2, 9},   // The Shawshank Redemption
            new int[]{1, 3, 8},   // Pulp Fiction
            new int[]{1, 5, 7},   // Fight Club
            new int[]{1, 10, 9},  // Parasite
            // User 2 (Bob Johnson)
            new int[]{2, 1, 9},   // Inception
            new int[]{2, 4, 10},  // The Dark Knight
            new int[]{2, 6, 8},   // Forrest Gump
            new int[]{2, 8, 9},   // Interstellar
            new int[]{2, 11, 10}, // Spirited Away
            // User 3 (Alice Williams)
            new int[]{3, 2, 10},  // The Shawshank Redemption
            new int[]{3, 6, 9},   // Forrest Gump
            new int[]{3, 7, 9},   // The Godfather
            new int[]{3, 9, 8},   // The Lord of the Rings
            new int[]{3, 12, 9},  // Amélie
            // User 4 (Charlie Brown)
            new int[]{4, 0, 7},   // The Matrix
            new int[]{4, 3, 9},   // Pulp Fiction
            new int[]{4, 5, 8},   // Fight Club
            new int[]{4, 13, 8},  // City of God
            // User 5 (Diana Prince)
            new int[]{5, 1, 8},   // Inception
            new int[]{5, 4, 9},   // The Dark Knight
            new int[]{5, 8, 10},  // Interstellar
            new int[]{5, 10, 10}, // Parasite
            new int[]{5, 14, 9},  // Oldboy
            // User 6 (Eve Davis)
            new int[]{6, 2, 9},   // The Shawshank Redemption
            new int[]{6, 6, 8},   // Forrest Gump
            new int[]{6, 9, 9},   // The Lord of the Rings
            new int[]{6, 11, 9},  // Spirited Away
            new int[]{6, 12, 8},  // Amélie
            // User 7 (Frank Miller)
            new int[]{7, 0, 9},   // The Matrix
            new int[]{7, 3, 10},  // Pulp Fiction
            new int[]{7, 5, 9},   // Fight Club
            new int[]{7, 7, 10},  // The Godfather
            new int[]{7, 13, 9},  // City of God
            new int[]{7, 14, 8}   // Oldboy
    );
}
