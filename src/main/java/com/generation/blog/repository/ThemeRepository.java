package com.generation.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generation.blog.model.Theme;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
	public List<Theme> findAllByDescriptionContainingIgnoreCase(String description);
	
}
