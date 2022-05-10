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

import com.generation.blog.repository.ThemeRepository;
import com.generation.blog.model.Theme;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/themes")
public class ThemeController {
	
	@Autowired
	private ThemeRepository repository;

	@GetMapping
	public ResponseEntity<List<Theme>> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/id")
	public ResponseEntity<Theme> getById(@PathVariable long id) {
		return repository.findById(id).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Theme>> getByName(@PathVariable String name) {
		return ResponseEntity.ok(repository.findAllByDescriptionContainingIgnoreCase(name));
	}
	
	@PostMapping
	public ResponseEntity<Theme> post(@RequestBody Theme theme) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(repository.save(theme));
	}
	
	@PutMapping
	public ResponseEntity<Theme> put(@RequestBody Theme theme) {
		return ResponseEntity.ok(repository.save(theme));
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		repository.deleteById(id);
	}
}
