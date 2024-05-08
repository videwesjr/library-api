package com.project.libraryApi.mapper;

import com.project.libraryApi.dto.BookDTO;
import com.project.libraryApi.model.Book;

public class BookMapper {

    public static Book toBook(Book originalBook, BookDTO newBook) {
        originalBook.setTitle(newBook.getTitle());
        return originalBook;
    }

    public static Book toBook(BookDTO newBook) {
        return new Book(newBook.getTitle());
    }
}
