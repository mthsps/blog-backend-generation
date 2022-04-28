package com.generation.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blog.model.Post;
import com.generation.blog.repository.PostRepository;

@RestController
@RequestMapping("/posts")
@CrossOrigin("*")
public class PostController {
	
	@Autowired
	private PostRepository repository;
	
	@GetMapping
    ResponseEntity<List<Post>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }
}
