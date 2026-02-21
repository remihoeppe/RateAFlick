package com.example.demo.config.seed;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.config.seed.UserSeedData.USERS;

@Component
public class UserSeeder {

    private static final Logger logger = LoggerFactory.getLogger(UserSeeder.class);

    public void seed(UserRepository userRepository) {
        if (userRepository.count() > 0) {
            logger.info("Users already exist, skipping user seeding");
            return;
        }
        logger.info("Seeding users...");
        List<User> users = USERS.stream()
                .map(row -> {
                    User user = new User();
                    user.setName(row.name());
                    user.setEmail(row.email());
                    return user;
                })
                .collect(Collectors.toList());
        userRepository.saveAll(users);
        logger.info("Seeded {} users", users.size());
    }
}
