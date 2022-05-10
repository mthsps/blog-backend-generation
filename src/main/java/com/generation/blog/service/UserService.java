package com.generation.blog.service;

import com.generation.blog.model.User;
import com.generation.blog.model.UserLogin;
import com.generation.blog.repository.UserRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository repository;

    public Optional<User> register (User user) {

        if (repository.findByUsername(user.getUsername()).isEmpty()) {
            return Optional.empty();
        }

        user.setPassword(cryptPassword(user.getPassword()));
        return Optional.of(repository.save(user));

    }


    public Optional<UserLogin> authenticateUser (Optional<UserLogin> userLogin) {

        Optional<User> user = repository.findByUsername(userLogin.get().getUsername());

        if (user.isPresent()) {
           if (passwordMatcher(userLogin.get().getPassword(), user.get().getPassword())) {
               userLogin.get().setId(user.get().getId());
               userLogin.get().setName(user.get().getName());
               userLogin.get().setImageUrl(user.get().getImageUrl());
               userLogin.get().setToken(generateToken(userLogin.get().getUsername(), userLogin.get().getPassword()));
               userLogin.get().setPassword(user.get().getPassword());

               return userLogin;
           }
        }

        return Optional.empty();
    }

    private String cryptPassword(String password) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(password);
    }
    private boolean passwordMatcher(String typedPassword, String savedPassword) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.matches(typedPassword, savedPassword);
    }

    private String generateToken(String username, String password) {

        String token = username + ":" + password;
        byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(StandardCharsets.US_ASCII));
        return "Basic " + new String(tokenBase64);

    }


}
