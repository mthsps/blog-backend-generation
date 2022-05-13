package com.generation.blog.repository;

import com.generation.blog.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    void start() {

        userRepository
                .save(new User(0L, "John Ares", "john1", "john@email.com", "john1234"));
        userRepository
                .save(new User(0L, "Mars Ares", "mars1", "mars@email.com", "mars1234"));
        userRepository
                .save(new User(0L, "Gaia Lois", "gaia1", "gaia@email.com", "gaia1234"));
        userRepository
                .save(new User(0L, "Aloes Ares", "aloes1", "aloes@email.com", "aloes1234"));
    }

    @Test
    @DisplayName("Return an user")
    public void returnUser() {
        Optional<User> user = userRepository.findByUsername("john1");
        assertEquals("john1", user.get().getUsername());
    }


    @Test
    @DisplayName("Return three users")
    public void returnThreeUsers() {
        List<User> users = userRepository.findAllByNameContainingIgnoreCase("Ares");
        assertEquals(3, users.size());
        assertEquals("John Ares", users.get(0).getName());
        assertEquals("Mars Ares", users.get(1).getName());
        assertEquals("Aloes Ares", users.get(2).getName());
    }
}
