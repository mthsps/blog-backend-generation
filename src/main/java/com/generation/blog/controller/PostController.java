package com.generation.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	public PostController(PostRepository repository) {
		this.repository = repository;
	}

	@GetMapping
    ResponseEntity<List<Post>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }
	
	@GetMapping("/{id}")
	ResponseEntity<Post> GetById(@PathVariable long id) {
		return repository.findById(id)
				.map(response -> ResponseEntity.ok(response))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/title/{title}")
	public ResponseEntity<List<Post>> GetByTitle(@PathVariable String title) {
		return ResponseEntity.ok(repository.findAllByTitleContainingIgnoreCase(title));
	}
	
	@PostMapping
	public ResponseEntity<Post> post (@RequestBody Post newPost){

		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newPost));
	}
	
	@PutMapping
	public ResponseEntity<Post> put (@RequestBody Post newPost){

		return ResponseEntity.status(HttpStatus.OK).body(repository.save(newPost));
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		repository.deleteById(id);
		
	}
	
	
	
}
