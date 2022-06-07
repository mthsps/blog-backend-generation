package com.generation.blog.service;

import com.generation.blog.exception.ThemeAlreadyExistsException;
import com.generation.blog.model.Theme;
import com.generation.blog.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ThemeServiceImpl implements ThemeService{

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    public ThemeServiceImpl(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    @Override
    public Theme saveTheme(Theme theme) {
        List<Theme> themes = themeRepository.findAll();
        for (Theme item : themes) {
            if (item.getDescription().equalsIgnoreCase(theme.getDescription()))
                throw new ThemeAlreadyExistsException();
        }
        return themeRepository.save(theme);
    }

}
