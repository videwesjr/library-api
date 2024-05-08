package com.project.libraryApi.service;

import com.project.libraryApi.dto.BookDTO;
import com.project.libraryApi.exception.BookNotFoundException;
import com.project.libraryApi.model.Book;
import com.project.libraryApi.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class BookServiceTest {
  @InjectMocks
  private BookService bookService;
  @Mock
  private BookRepository bookRepository;

  @Test
  public void getAllBooksTest() {
    Book book = new Book(1L, "test");
    List<Book> bookList = new ArrayList<>();
    bookList.add(book);
    when(bookRepository.findAll()).thenReturn(bookList);
    assertEquals(bookService.getAllBooks(), bookList);
  }

  @Test
  public void getBookByIdTest() {
    Long bookId = 1L;
    Book book = new Book(bookId, "test");
    when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
    assertEquals(bookService.getBookById(bookId), book);
  }

  @Test
  public void getBookByIdNotFoundExceptionTest() {
    Long bookId = 1L;
    doThrow(BookNotFoundException.class).when(bookRepository).findById(any());
    Assertions.assertThrows(
            BookNotFoundException.class,
            () -> {
              bookService.getBookById(bookId);
            });
  }

  @Test
  public void saveBookTest() {
    Book book = new Book(1L, "test");
    BookDTO bookDTO = new BookDTO("test");
    when(bookRepository.save(any())).thenReturn(book);
    assertEquals(bookService.saveBook(bookDTO).getTitle(), book.getTitle());
  }

  @Test
  public void updateBookTest() {
    Book book = new Book(1L, "test");
    BookDTO bookDTO = new BookDTO("test");
    when(bookRepository.findById(any())).thenReturn(Optional.of(book));
    when(bookRepository.save(any())).thenReturn(book);
    assertEquals(bookService.updateBook(1L, bookDTO).getTitle(), book.getTitle());
  }

  @Test
  public void deleteBookTest() {
    doNothing().when(bookRepository).deleteById(any());
    bookService.deleteBook(1L);
    verify(bookRepository, times(1)).deleteById(any());
  }
}
