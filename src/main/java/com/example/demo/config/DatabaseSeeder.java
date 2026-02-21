package com.example.demo.config;

import com.example.demo.config.seed.*;
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
import org.springframework.jdbc.core.JdbcTemplate;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class DatabaseSeeder {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);

    @Value("${app.seeder.enabled:true}")
    private boolean seederEnabled;

    @Value("${app.seeder.drop-and-reseed:false}")
    private boolean dropAndReseed;

    @Bean
    @Profile("!prod")
    public CommandLineRunner seedDatabase(
            UserRepository userRepository,
            MovieRepository movieRepository,
            DirectorRepository directorRepository,
            RatingRepository ratingRepository,
            UserSeeder userSeeder,
            DirectorSeeder directorSeeder,
            MovieSeeder movieSeeder,
            RatingSeeder ratingSeeder,
            SchemaReset schemaReset,
            JdbcTemplate jdbcTemplate,
            EntityManagerFactory entityManagerFactory) {
        return args -> {
            if (!seederEnabled) {
                logger.info("Database seeder is disabled");
                return;
            }
            if (dropAndReseed) {
                logger.warn("Drop-and-reseed is enabled: dropping all tables and recreating schema...");
                schemaReset.dropAllTables(jdbcTemplate);
                schemaReset.recreateSchema(entityManagerFactory);
                logger.info("Schema recreated, proceeding to seed data.");
            }
            logger.info("Starting database seeding...");
            userSeeder.seed(userRepository);
            directorSeeder.seed(directorRepository);
            movieSeeder.seed(movieRepository, directorRepository);
            ratingSeeder.seed(userRepository, movieRepository, ratingRepository);
            logger.info("Database seeding completed successfully!");
        };
    }
}
