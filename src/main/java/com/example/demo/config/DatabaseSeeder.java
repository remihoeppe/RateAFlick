package com.example.demo.config;

import com.example.demo.models.Director;
import com.example.demo.models.Movie;
import com.example.demo.models.Rating;
import com.example.demo.models.User;
import com.example.demo.repositories.DirectorRepository;
import com.example.demo.repositories.MovieRepository;
import com.example.demo.repositories.RatingRepository;
import com.example.demo.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Configuration
public class DatabaseSeeder {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);

    @Value("${app.seeder.enabled:true}")
    private boolean seederEnabled;

    @Bean
    @Profile("!prod") // Don't run seeder in production profile
    public CommandLineRunner seedDatabase(UserRepository userRepository, MovieRepository movieRepository,
            DirectorRepository directorRepository, RatingRepository ratingRepository) {
        return args -> {
            if (!seederEnabled) {
                logger.info("Database seeder is disabled");
                return;
            }

            logger.info("Starting database seeding...");

            // Seed Users
            seedUsers(userRepository);

            // Seed Directors (before movies)
            seedDirectors(directorRepository);

            // Seed Movies
            seedMovies(movieRepository, directorRepository);

            // Seed Ratings (must be after users and movies are seeded)
            seedRatings(userRepository, movieRepository, ratingRepository);

            logger.info("Database seeding completed successfully!");
        };
    }

    private void seedUsers(UserRepository userRepository) {
        if (userRepository.count() > 0) {
            logger.info("Users already exist, skipping user seeding");
            return;
        }

        logger.info("Seeding users...");

        List<User> users = Arrays.asList(
                createUser("John Doe", "john.doe@example.com"),
                createUser("Jane Smith", "jane.smith@example.com"),
                createUser("Bob Johnson", "bob.johnson@example.com"),
                createUser("Alice Williams", "alice.williams@example.com"),
                createUser("Charlie Brown", "charlie.brown@example.com"),
                createUser("Diana Prince", "diana.prince@example.com"),
                createUser("Eve Davis", "eve.davis@example.com"),
                createUser("Frank Miller", "frank.miller@example.com"));

        userRepository.saveAll(users);
        logger.info("Seeded {} users", users.size());
    }

    private void seedDirectors(DirectorRepository directorRepository) {
        if (directorRepository.count() > 0) {
            logger.info("Directors already exist, skipping director seeding");
            return;
        }
        logger.info("Seeding directors...");
        List<String> directorNames = Arrays.asList(
                "The Wachowskis", "Christopher Nolan", "Frank Darabont", "Quentin Tarantino",
                "David Fincher", "Robert Zemeckis", "Francis Ford Coppola", "Peter Jackson",
                "Bong Joon-ho", "Hayao Miyazaki", "Jean-Pierre Jeunet", "Fernando Meirelles",
                "Park Chan-wook");
        for (String name : directorNames) {
            Director d = new Director(name);
            directorRepository.save(d);
        }
        logger.info("Seeded {} directors", directorNames.size());
    }

    private void seedMovies(MovieRepository movieRepository, DirectorRepository directorRepository) {
        if (movieRepository.count() > 0) {
            logger.info("Movies already exist, skipping movie seeding");
            return;
        }

        logger.info("Seeding movies...");

        List<Movie> movies = Arrays.asList(
                createMovie(directorRepository, "The Matrix", 1999, "The Wachowskis", "English"),
                createMovie(directorRepository, "Inception", 2010, "Christopher Nolan", "English"),
                createMovie(directorRepository, "The Shawshank Redemption", 1994, "Frank Darabont", "English"),
                createMovie(directorRepository, "Pulp Fiction", 1994, "Quentin Tarantino", "English"),
                createMovie(directorRepository, "The Dark Knight", 2008, "Christopher Nolan", "English"),
                createMovie(directorRepository, "Fight Club", 1999, "David Fincher", "English"),
                createMovie(directorRepository, "Forrest Gump", 1994, "Robert Zemeckis", "English"),
                createMovie(directorRepository, "The Godfather", 1972, "Francis Ford Coppola", "English"),
                createMovie(directorRepository, "Interstellar", 2014, "Christopher Nolan", "English"),
                createMovie(directorRepository, "The Lord of the Rings: The Fellowship of the Ring", 2001, "Peter Jackson", "English"),
                createMovie(directorRepository, "Parasite", 2019, "Bong Joon-ho", "Korean"),
                createMovie(directorRepository, "Spirited Away", 2001, "Hayao Miyazaki", "Japanese"),
                createMovie(directorRepository, "Amélie", 2001, "Jean-Pierre Jeunet", "French"),
                createMovie(directorRepository, "City of God", 2002, "Fernando Meirelles", "Portuguese"),
                createMovie(directorRepository, "Oldboy", 2003, "Park Chan-wook", "Korean"));

        movieRepository.saveAll(movies);
        logger.info("Seeded {} movies", movies.size());
    }

    private User createUser(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        return user;
    }

    private Movie createMovie(DirectorRepository directorRepository,
            String title, int releaseYear, String directorName, String language) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setReleaseYear(releaseYear);
        movie.setLanguage(language);
        directorRepository.findAll().stream()
                .filter(d -> d.getName().equals(directorName))
                .findFirst()
                .ifPresent(movie::setDirector);
        return movie;
    }

    private void seedRatings(UserRepository userRepository, MovieRepository movieRepository,
            RatingRepository ratingRepository) {
        if (ratingRepository.count() > 0) {
            logger.info("Ratings already exist, skipping rating seeding");
            return;
        }

        // Ensure we have users and movies before seeding ratings
        if (userRepository.count() == 0 || movieRepository.count() == 0) {
            logger.warn("Cannot seed ratings: users or movies are missing. Please seed users and movies first.");
            return;
        }

        logger.info("Seeding ratings...");

        // Get all users and movies (assuming they're seeded in order)
        List<User> users = userRepository.findAll();
        List<Movie> movies = movieRepository.findAll();

        if (users.isEmpty() || movies.isEmpty()) {
            logger.warn("No users or movies found for rating seeding");
            return;
        }

        // Create diverse ratings: different users rating different movies with various
        // scores
        // Format: (userIndex, movieIndex, score)
        int[][] ratingData = {
                // User 0 (John Doe) ratings
                { 0, 0, 9 }, // The Matrix
                { 0, 1, 10 }, // Inception
                { 0, 4, 9 }, // The Dark Knight
                { 0, 7, 10 }, // The Godfather

                // User 1 (Jane Smith) ratings
                { 1, 0, 8 }, // The Matrix
                { 1, 2, 9 }, // The Shawshank Redemption
                { 1, 3, 8 }, // Pulp Fiction
                { 1, 5, 7 }, // Fight Club
                { 1, 10, 9 }, // Parasite

                // User 2 (Bob Johnson) ratings
                { 2, 1, 9 }, // Inception
                { 2, 4, 10 }, // The Dark Knight
                { 2, 6, 8 }, // Forrest Gump
                { 2, 8, 9 }, // Interstellar
                { 2, 11, 10 }, // Spirited Away

                // User 3 (Alice Williams) ratings
                { 3, 2, 10 }, // The Shawshank Redemption
                { 3, 6, 9 }, // Forrest Gump
                { 3, 7, 9 }, // The Godfather
                { 3, 9, 8 }, // The Lord of the Rings
                { 3, 12, 9 }, // Amélie

                // User 4 (Charlie Brown) ratings
                { 4, 0, 7 }, // The Matrix
                { 4, 3, 9 }, // Pulp Fiction
                { 4, 5, 8 }, // Fight Club
                { 4, 13, 8 }, // City of God

                // User 5 (Diana Prince) ratings
                { 5, 1, 8 }, // Inception
                { 5, 4, 9 }, // The Dark Knight
                { 5, 8, 10 }, // Interstellar
                { 5, 10, 10 }, // Parasite
                { 5, 14, 9 }, // Oldboy

                // User 6 (Eve Davis) ratings
                { 6, 2, 9 }, // The Shawshank Redemption
                { 6, 6, 8 }, // Forrest Gump
                { 6, 9, 9 }, // The Lord of the Rings
                { 6, 11, 9 }, // Spirited Away
                { 6, 12, 8 }, // Amélie

                // User 7 (Frank Miller) ratings
                { 7, 0, 9 }, // The Matrix
                { 7, 3, 10 }, // Pulp Fiction
                { 7, 5, 9 }, // Fight Club
                { 7, 7, 10 }, // The Godfather
                { 7, 13, 9 }, // City of God
                { 7, 14, 8 } // Oldboy
        };

        int createdCount = 0;
        for (int[] data : ratingData) {
            int userIndex = data[0];
            int movieIndex = data[1];
            int score = data[2];

            // Validate indices
            if (userIndex >= users.size() || movieIndex >= movies.size()) {
                logger.warn("Skipping rating: user index {} or movie index {} out of bounds", userIndex, movieIndex);
                continue;
            }

            User user = users.get(userIndex);
            Movie movie = movies.get(movieIndex);

            // Check if rating already exists (respect unique constraint)
            Optional<Rating> existingRating = ratingRepository.findByUser_IdAndMovie_Id(user.getId(), movie.getId());
            if (existingRating.isPresent()) {
                logger.debug("Rating already exists for user {} and movie {}, skipping", user.getId(), movie.getId());
                continue;
            }

            Rating rating = new Rating();
            rating.setUser(user);
            rating.setMovie(movie);
            rating.setScore(score);

            ratingRepository.save(rating);
            createdCount++;
        }

        logger.info("Seeded {} ratings", createdCount);
    }
}
