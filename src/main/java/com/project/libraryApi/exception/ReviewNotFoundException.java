package com.project.libraryApi.exception;

public class ReviewNotFoundException extends RuntimeException {
public ReviewNotFoundException(Long id) {
  super("Review not found. id: " + id);
  }
}
