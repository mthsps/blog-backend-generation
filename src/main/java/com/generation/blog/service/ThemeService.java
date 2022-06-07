package com.generation.blog.service;

import com.generation.blog.exception.ThemeAlreadyExistsException;
import com.generation.blog.model.Theme;
import org.springframework.stereotype.Service;

@Service
public interface ThemeService {
    Theme saveTheme(Theme theme) throws ThemeAlreadyExistsException;
}