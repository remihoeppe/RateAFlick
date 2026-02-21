package com.example.demo.config.seed;

import com.example.demo.models.Director;
import com.example.demo.repositories.DirectorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.example.demo.config.seed.DirectorSeedData.DIRECTOR_NAMES;

@Component
public class DirectorSeeder {

    private static final Logger logger = LoggerFactory.getLogger(DirectorSeeder.class);

    public void seed(DirectorRepository directorRepository) {
        if (directorRepository.count() > 0) {
            logger.info("Directors already exist, skipping director seeding");
            return;
        }
        logger.info("Seeding directors...");
        for (String name : DIRECTOR_NAMES) {
            directorRepository.save(new Director(name));
        }
        logger.info("Seeded {} directors", DIRECTOR_NAMES.size());
    }
}
