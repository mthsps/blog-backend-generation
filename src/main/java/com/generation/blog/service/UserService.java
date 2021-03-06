package com.generation.blog.service;

import com.generation.blog.model.User;
import com.generation.blog.model.UserLogin;
import com.generation.blog.repository.UserRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> register(User user) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return Optional.empty();
        }

        user.setPassword(encryptPassword(user.getPassword()));
        return Optional.of(userRepository.save(user));

    }

    public Optional<User> update(User user) {

        if (userRepository.findById(user.getId()).isPresent()) {

            user.setPassword((encryptPassword(user.getPassword())));
            return Optional.of(userRepository.save(user));

        }
        return Optional.empty();

    }

    public Optional<UserLogin> authenticate(Optional<UserLogin> userLogin) {

        Optional<User> user = userRepository.findByUsername(userLogin.get().getUsername());

        if (user.isPresent()) {
           if (passwordMatcher(userLogin.get().getPassword(), user.get().getPassword())) {
               userLogin.get().setId(user.get().getId());
               userLogin.get().setName(user.get().getName());
               userLogin.get().setEmail(user.get().getEmail());
               userLogin.get().setImageUrl(user.get().getImageUrl());
               userLogin.get().setType(user.get().getType());
               userLogin.get().setToken(generateToken(userLogin.get().getUsername(), userLogin.get().getPassword()));
               userLogin.get().setPassword(user.get().getPassword());

               return userLogin;
           }
        }

        return Optional.empty();
    }

    private String encryptPassword(String password) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(password);
    }
    private boolean passwordMatcher(String typedPassword, String savedPassword) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.matches(typedPassword, savedPassword);
    }

    private String generateToken(String username, String password) {

        String token = username + ":" + password;
        byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
        return "Basic " + new String(tokenBase64);

    }


}
