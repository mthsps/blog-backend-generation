package com.generation.blog.controller;

import com.generation.blog.model.User;
import com.generation.blog.model.UserLogin;
import com.generation.blog.repository.UserRepository;
import com.generation.blog.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Test
    @Order(1)
    @DisplayName("Register new user")
    public void registerNewUser() {

        HttpEntity<User> request = new HttpEntity<User>(
                new User(0L, "Mars Ares", "mars1", "mars@email.com", "mars1234"));

        ResponseEntity<User> response = testRestTemplate
                .exchange("/users/signup", HttpMethod.POST, request, User.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(request.getBody().getName(), response.getBody().getName());
        assertEquals(response.getBody().getUsername(), response.getBody().getUsername());
    }

    @Test
    @Order(2)
    @DisplayName("Log in user")
    public void loginUser() {

        User user = new User(0L, "Mars Ares", "mars1", "mars@email.com", "mars1234");

        Optional<UserLogin> userLogin = userService.authenticate(
                Optional.of(new UserLogin(user.getUsername(), user.getPassword())));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", userLogin.get().getToken());

        HttpEntity<Optional<UserLogin>> request = new HttpEntity<Optional<UserLogin>>(
                Optional.of(new UserLogin(user.getUsername(), user.getPassword())), httpHeaders);

        ResponseEntity<User> response = testRestTemplate
                .exchange("/users/login", HttpMethod.POST, request, User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(3)
    @DisplayName("User duplicates")
    public void userDuplicates() {

        userService.register(
                new User(0L, "Gaia Lois", "gaia1", "gaia@email.com", "gaia1234"));

        HttpEntity<User> request = new HttpEntity<User>(
                new User(0L, "Gaia Lois", "gaia1", "gaia@email.com", "gaia1234"));

        ResponseEntity<User> response = testRestTemplate
                .exchange("/users/signup", HttpMethod.POST, request, User.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(4)
    @DisplayName("Update user")
    public void updateUser() {

        Optional<User> user = userService
                .register(new User(0L, "Aloes Ares", "aloes1", "aloes@email.com", "aloes1234"));

        User updatedUser = new User(
                user.get().getId(), "Aloes Aragon", "aragon1", "aloes@email.com", "aloes1234");

        HttpEntity<User> request = new HttpEntity<User>(updatedUser);

        ResponseEntity<User> response = testRestTemplate
                .withBasicAuth("root", "root")
                .exchange("/users/update", HttpMethod.PUT, request, User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser.getName(), response.getBody().getName());
        assertEquals(updatedUser.getUsername(), response.getBody().getUsername());
    }

    @Test
    @Order(5)
    @DisplayName("Get all users")
    public void getAllUsers() {

        userService.register(
                new User(0L, "Alma Ares", "alma1", "alma@email.com", "alma1234"));
        userService.register(
                new User(0L, "Upt Lois", "upt1", "upt@email.com", "upt1234"));

        ResponseEntity<String> response = testRestTemplate
                .withBasicAuth("root", "root")
                .exchange("/users", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(6)
    @DisplayName("Get user by id")
    public void getById() {

        Optional<User> user = userService
                .register(new User(0L, "Alias Ares", "alias1", "alias@email.com", "alias1234"));

        ResponseEntity<User> response = testRestTemplate
                .withBasicAuth("root", "root")
                .exchange("/users/" + user.get().getId(), HttpMethod.GET, null, User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user.get().getUsername(), response.getBody().getUsername());

    }

    @Test
    @Order(7)
    @DisplayName("Delete user")
    public void deleteUser() {

        Optional<User> user = userService
                .register(new User(0L, "Alis Ares", "alis1", "alis@email.com", "alis1234"));

        ResponseEntity<String> response = testRestTemplate
                .withBasicAuth("root", "root")
                .exchange("/users/" + user.get().getId(), HttpMethod.DELETE, null, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }


}
