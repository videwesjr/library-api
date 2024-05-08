package com.project.libraryApi.dto;

import com.project.libraryApi.model.BaseModel;
import com.project.libraryApi.model.Book;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class BookListDTO extends BaseModel {
    List<Book> books;

    public BookListDTO(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
