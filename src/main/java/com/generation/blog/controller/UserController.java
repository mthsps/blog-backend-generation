package com.generation.blog.controller;

import com.generation.blog.model.Theme;
import com.generation.blog.model.User;
import com.generation.blog.model.UserLogin;
import com.generation.blog.repository.UserRepository;
import com.generation.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

     @Autowired
     private UserService userService;

     @Autowired
     private UserRepository userRepository;

     @GetMapping
     public ResponseEntity<List<User>> getAll() {
         return ResponseEntity.ok(userRepository.findAll());
     }

    @PostMapping("/login")
    public ResponseEntity<UserLogin> login(@RequestBody Optional<UserLogin> user) {
         return userService.authenticateUser(user).map(ResponseEntity::ok)
                 .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()); }

    @PostMapping("/signup")
    public ResponseEntity<User> postUser(@Valid @RequestBody User user) {
         return userService.register(user)
                 .map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(resp))
                 .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
     }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return userRepository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<User> put(@RequestBody User user) {
        return ResponseEntity.ok(userRepository.save(user));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

}
