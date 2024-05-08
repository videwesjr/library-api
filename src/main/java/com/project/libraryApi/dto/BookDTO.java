package com.project.libraryApi.dto;

import com.project.libraryApi.model.BaseModel;
import jakarta.validation.constraints.NotBlank;

public class BookDTO extends BaseModel {
    @NotBlank(message = "title must not be BLANK")
    private String title;

    public BookDTO() {
    }

    public BookDTO(String name) {
        this.title = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
