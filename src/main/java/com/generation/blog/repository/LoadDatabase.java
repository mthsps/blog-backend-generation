package com.generation.blog.repository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.generation.blog.model.Post;
import com.generation.blog.model.Theme;

@Configuration
class LoadDatabase {

  @Bean
  CommandLineRunner initDatabase(PostRepository postRepository, ThemeRepository themeRepository) {

    return args -> {
    	
    	Theme theme1 = themeRepository.save(new Theme("Python"));
    	Theme theme2 = themeRepository.save(new Theme("Java"));
    	Theme theme3 = themeRepository.save(new Theme("SQL"));
    	
    	postRepository.save(new Post("Python and Machine Learning", "Content", theme1));
    	postRepository.save(new Post("Python and Statistics", "Content", theme1));
    	postRepository.save(new Post("Python and AI", "Content", theme1));
    	postRepository.save(new Post("Python and Calculus", "Content", theme1));
    	postRepository.save(new Post("Java and Spring", "Content", theme2));
    	postRepository.save(new Post("SQL and Data Analysis", "Content", theme3));

      
    };
  }
}