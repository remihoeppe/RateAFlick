package com.example.demo.config.seed;

import com.example.demo.models.Movie;
import com.example.demo.repositories.DirectorRepository;
import com.example.demo.repositories.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.config.seed.MovieSeedData.MOVIES;

@Component
public class MovieSeeder {

    private static final Logger logger = LoggerFactory.getLogger(MovieSeeder.class);

    public void seed(MovieRepository movieRepository, DirectorRepository directorRepository) {
        if (movieRepository.count() > 0) {
            logger.info("Movies already exist, skipping movie seeding");
            return;
        }
        logger.info("Seeding movies...");
        List<Movie> movies = MOVIES.stream()
                .map(row -> {
                    Movie movie = new Movie();
                    movie.setTitle(row.title());
                    movie.setReleaseYear(row.releaseYear());
                    movie.setLanguage(row.language());
                    directorRepository.findAll().stream()
                            .filter(d -> d.getName().equals(row.directorName()))
                            .findFirst()
                            .ifPresent(movie::setDirector);
                    return movie;
                })
                .collect(Collectors.toList());
        movieRepository.saveAll(movies);
        logger.info("Seeded {} movies", movies.size());
    }
}
