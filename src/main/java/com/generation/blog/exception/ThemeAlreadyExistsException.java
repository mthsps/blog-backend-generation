package com.generation.blog.exception;


public class ThemeAlreadyExistsException extends RuntimeException {
    public ThemeAlreadyExistsException(String message) {
        super(message);
    }
    public ThemeAlreadyExistsException() {
    }
}
