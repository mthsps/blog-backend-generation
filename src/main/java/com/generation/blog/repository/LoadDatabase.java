package com.generation.blog.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.generation.blog.model.Post;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(PostRepository postRepository) {

    return args -> {
      postRepository.save(new Post("Title1", "Content1"));
      postRepository.save(new Post("Title2", "Content2"));

      postRepository.findAll().forEach(post -> log.info("Preloaded " + post));

      
    };
  }
}