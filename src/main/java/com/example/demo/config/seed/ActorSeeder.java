package com.example.demo.config.seed;

import com.example.demo.models.Actor;
import com.example.demo.models.Movie;
import com.example.demo.repositories.ActorRepository;
import com.example.demo.repositories.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.example.demo.config.seed.ActorSeedData.ACTORS;
import static com.example.demo.config.seed.MovieSeedData.MOVIES;

@Component
public class ActorSeeder {

    private static final Logger logger = LoggerFactory.getLogger(ActorSeeder.class);

    /**
     * Fills the Actors table and links each actor to movies by movie_id (movie_actors table).
     * Run after MovieSeeder. Resolves each actor's movie indices to actual movie_id and persists links.
     */
    public void seed(ActorRepository actorRepository, MovieRepository movieRepository) {
        if (ACTORS.isEmpty()) {
            return;
        }
        if (actorRepository.count() > 0) {
            logger.info("Actors already exist, skipping actor seeding");
            return;
        }

        // Build map title|year -> Movie so we can get movies in MOVIES order
        Map<String, Movie> byTitleYear = new HashMap<>();
        movieRepository.findAll().forEach(m -> byTitleYear.put(m.getTitle() + "|" + m.getReleaseYear(), m));

        // Movies in same order as MOVIES; index i -> movie (so movieIds in ActorRow refer to this list)
        List<Movie> moviesInOrder = MOVIES.stream()
                .map(row -> byTitleYear.get(row.title() + "|" + row.releaseYear()))
                .filter(Objects::nonNull)
                .toList();

        // 1) Create all actors
        logger.info("Seeding {} actors...", ACTORS.size());
        for (ActorSeedData.ActorRow row : ACTORS) {
            if (actorRepository.findByName(row.actorName()).isEmpty()) {
                actorRepository.save(new Actor(row.actorName()));
            }
        }

        // 2) Link each actor to movies by movie_id (movieIds are indices into moviesInOrder)
        for (ActorSeedData.ActorRow row : ACTORS) {
            var actorOpt = actorRepository.findByName(row.actorName());
            if (actorOpt.isEmpty()) {
                continue;
            }
            var actor = actorOpt.get();
            for (Integer index : row.movieIds()) {
                if (index < 0 || index >= moviesInOrder.size()) {
                    continue;
                }
                Movie movie = moviesInOrder.get(index);
                if (movie != null && !movie.getActors().contains(actor)) {
                    movie.getActors().add(actor);
                }
            }
        }

        movieRepository.saveAll(moviesInOrder);
        logger.info("Seeded {} actors and linked via movie_id", actorRepository.count());
    }
}
