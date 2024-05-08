package com.project.libraryApi.dto;

import com.project.libraryApi.model.BaseModel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReviewDTO extends BaseModel {
    @NotNull(message = "idUser must not be NULL")
    private Long idUser;
    @NotNull(message = "idBook must not be NULL")
    private Long idBook;
    @NotNull(message = "rating must not be NULL")
    @Min(value = 1, message = "Rating should not be less than 1")
    @Max(value = 10, message = "Rating should not be greater than 10")
    private Integer rating;
    @NotBlank(message = "description must not be BLANK")
    private String description;

    public ReviewDTO() {
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdBook() {
        return idBook;
    }

    public void setIdBook(Long idBook) {
        this.idBook = idBook;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
