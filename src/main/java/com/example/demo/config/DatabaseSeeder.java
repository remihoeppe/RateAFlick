package com.example.demo.config;

import com.example.demo.models.Movie;
import com.example.demo.models.User;
import com.example.demo.repositories.MovieRepository;
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

@Configuration
public class DatabaseSeeder {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);

    @Value("${app.seeder.enabled:true}")
    private boolean seederEnabled;

    @Bean
    @Profile("!prod") // Don't run seeder in production profile
    public CommandLineRunner seedDatabase(UserRepository userRepository, MovieRepository movieRepository) {
        return args -> {
            if (!seederEnabled) {
                logger.info("Database seeder is disabled");
                return;
            }

            logger.info("Starting database seeding...");

            // Seed Users
            seedUsers(userRepository);

            // Seed Movies
            seedMovies(movieRepository);

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
                createUser("Frank Miller", "frank.miller@example.com")
        );

        userRepository.saveAll(users);
        logger.info("Seeded {} users", users.size());
    }

    private void seedMovies(MovieRepository movieRepository) {
        if (movieRepository.count() > 0) {
            logger.info("Movies already exist, skipping movie seeding");
            return;
        }

        logger.info("Seeding movies...");

        List<Movie> movies = Arrays.asList(
                createMovie("The Matrix", 1999, "The Wachowskis", "English"),
                createMovie("Inception", 2010, "Christopher Nolan", "English"),
                createMovie("The Shawshank Redemption", 1994, "Frank Darabont", "English"),
                createMovie("Pulp Fiction", 1994, "Quentin Tarantino", "English"),
                createMovie("The Dark Knight", 2008, "Christopher Nolan", "English"),
                createMovie("Fight Club", 1999, "David Fincher", "English"),
                createMovie("Forrest Gump", 1994, "Robert Zemeckis", "English"),
                createMovie("The Godfather", 1972, "Francis Ford Coppola", "English"),
                createMovie("Interstellar", 2014, "Christopher Nolan", "English"),
                createMovie("The Lord of the Rings: The Fellowship of the Ring", 2001, "Peter Jackson", "English"),
                createMovie("Parasite", 2019, "Bong Joon-ho", "Korean"),
                createMovie("Spirited Away", 2001, "Hayao Miyazaki", "Japanese"),
                createMovie("Amélie", 2001, "Jean-Pierre Jeunet", "French"),
                createMovie("City of God", 2002, "Fernando Meirelles", "Portuguese"),
                createMovie("Oldboy", 2003, "Park Chan-wook", "Korean")
        );

        movieRepository.saveAll(movies);
        logger.info("Seeded {} movies", movies.size());
    }

    private User createUser(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        return user;
    }

    private Movie createMovie(String title, int releaseYear, String director, String language) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setReleaseYear(releaseYear);
        movie.setDirector(director);
        movie.setLanguage(language);
        return movie;
    }
}
