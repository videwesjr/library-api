package com.project.libraryApi.controller;

import com.project.libraryApi.dto.BookDTO;
import com.project.libraryApi.model.Book;
import com.project.libraryApi.service.BookService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    Logger log = LoggerFactory.getLogger(BookController.class);
    @Autowired
    private BookService bookService;

    @GetMapping()
    public ResponseEntity<List<Book>> listAllBooks() {
        log.info("Receive request to GET ALL books");
        return new ResponseEntity<>(
                bookService.getAllBooks(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        log.info("Receive request to GET book. id: ".concat(id.toString()));
        Book book = bookService.getBookById(id);
        log.info("Book find success: ".concat(book.toString()));
        return new ResponseEntity<>(
                book,
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> saveBook(@Valid @RequestBody BookDTO book) {
        log.info("Receive request to INSERT book: ".concat(book.toString()));
        return new ResponseEntity<>(
                bookService.saveBook(book),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO book) {
        log.info("Receive request to UPDATE book. id: ".concat(id.toString()).concat(" book: ".concat(book.toString())));
        return new ResponseEntity<>(
                bookService.updateBook(id, book),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id) {
        log.info("Receive request to DELETE book. id: ".concat(id.toString()));
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
