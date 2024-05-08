package com.project.libraryApi.exception;

public class BookNotFoundException extends RuntimeException {
public BookNotFoundException(Long id) {
  super("Book not found. id: " + id);
  }
}
