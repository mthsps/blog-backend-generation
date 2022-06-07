package com.generation.blog.controller;

import java.util.List;

import com.generation.blog.exception.ThemeAlreadyExistsException;
import com.generation.blog.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.generation.blog.repository.ThemeRepository;
import com.generation.blog.model.Theme;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/themes")
public class ThemeController {
	@Autowired
	private ThemeService themeService;
	@Autowired
	private ThemeRepository repository;

	@GetMapping
	public ResponseEntity<List<Theme>> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/id")
	public ResponseEntity<Theme> getById(@PathVariable Long id) {
		return repository.findById(id).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Theme>> getByName(@PathVariable String name) {
		return ResponseEntity.ok(repository.findAllByDescriptionContainingIgnoreCase(name));
	}
	
	@PostMapping
	public ResponseEntity<Theme> post(@RequestBody Theme theme) throws ThemeAlreadyExistsException {
		Theme savedTheme = themeService.saveTheme(theme);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(savedTheme);
	}
	
	@PutMapping
	public ResponseEntity<Theme> put(@RequestBody Theme theme) {
		return ResponseEntity.ok(repository.save(theme));
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		repository.deleteById(id);
	}

	@ExceptionHandler(value = ThemeAlreadyExistsException.class)
	public ResponseEntity<String> ThemeAlreadyExistsException(ThemeAlreadyExistsException themeAlreadyExistsException) {
		return new ResponseEntity<>("Theme already exists", HttpStatus.CONFLICT);
	}
}
