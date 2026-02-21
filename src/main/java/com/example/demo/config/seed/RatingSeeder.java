package com.example.demo.config.seed;

import com.example.demo.models.Movie;
import com.example.demo.models.Rating;
import com.example.demo.models.User;
import com.example.demo.repositories.MovieRepository;
import com.example.demo.repositories.RatingRepository;
import com.example.demo.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.example.demo.config.seed.RatingSeedData.RATINGS;

@Component
public class RatingSeeder {

    private static final Logger logger = LoggerFactory.getLogger(RatingSeeder.class);

    public void seed(UserRepository userRepository, MovieRepository movieRepository,
            RatingRepository ratingRepository) {
        if (ratingRepository.count() > 0) {
            logger.info("Ratings already exist, skipping rating seeding");
            return;
        }
        if (userRepository.count() == 0 || movieRepository.count() == 0) {
            logger.warn("Cannot seed ratings: users or movies are missing. Seed users and movies first.");
            return;
        }
        logger.info("Seeding ratings...");
        List<User> users = userRepository.findAll();
        List<Movie> movies = movieRepository.findAll();
        if (users.isEmpty() || movies.isEmpty()) {
            logger.warn("No users or movies found for rating seeding");
            return;
        }
        int createdCount = 0;
        for (int[] data : RATINGS) {
            int userIndex = data[0];
            int movieIndex = data[1];
            int score = data[2];
            if (userIndex >= users.size() || movieIndex >= movies.size()) {
                logger.warn("Skipping rating: user index {} or movie index {} out of bounds", userIndex, movieIndex);
                continue;
            }
            User user = users.get(userIndex);
            Movie movie = movies.get(movieIndex);
            Optional<Rating> existing = ratingRepository.findByUser_IdAndMovie_Id(user.getId(), movie.getId());
            if (existing.isPresent()) {
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
