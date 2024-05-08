package com.project.libraryApi.service;

import com.project.libraryApi.dto.BookDTO;
import com.project.libraryApi.exception.BookNotFoundException;
import com.project.libraryApi.mapper.BookMapper;
import com.project.libraryApi.model.Book;
import com.project.libraryApi.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"books"})
public class BookService {
    Logger log = LoggerFactory.getLogger(BookService.class);
    @Autowired
    private BookRepository bookRepository;

    @Cacheable("booksList")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public Book getBookById(Long id) {
        log.info("Finding book. id: ".concat(id.toString()));
        return bookRepository
                .findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Caching(evict = @CacheEvict(cacheNames = "booksList", allEntries = true),
            put = @CachePut(key = "#result.id", unless = "#result == null"))
    public Book saveBook(BookDTO book) {
        log.info("Saving book. ".concat(book.toString()));
        Book saveBook = bookRepository.save(BookMapper.toBook(book));
        log.info("Book saved success: ".concat(saveBook.toString()));
        return saveBook;
    }

    @CacheEvict(key = "#id")
    @Caching(evict = {@CacheEvict(cacheNames = "booksList", allEntries = true), @CacheEvict(key = "#id")})
    public void deleteBook(Long id) {
        log.info("Deleting book. id: ".concat(id.toString()));
        bookRepository.deleteById(id);
        log.info("Book deleted success: id ".concat(id.toString()));
    }

    @Caching(evict = @CacheEvict(cacheNames = "booksList", allEntries = true),
            put = @CachePut(key = "#result.id", unless = "#result == null"))
    public Book updateBook(Long id, BookDTO book) {
        log.info("Updating book. id: ".concat(id.toString()).concat(" new data: ".concat(book.toString())));
        Book originalBook = bookRepository
                .findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        Book saveBook = bookRepository.save(BookMapper.toBook(originalBook, book));
        log.info("Book updated success: id ".concat(id.toString()));
        return saveBook;
    }

}
