package com.project.libraryApi.mapper;

import com.project.libraryApi.dto.ReviewDTO;
import com.project.libraryApi.model.Review;

public class ReviewMapper {

    public static Review toReview(Review originalReview, ReviewDTO newReview) {
        originalReview.setDescription(newReview.getDescription());
        originalReview.setDescription(newReview.getDescription());
        originalReview.setIdBook(newReview.getIdBook());
        originalReview.setIdUser(newReview.getIdUser());
        return originalReview;
    }

    public static Review toReview(ReviewDTO newReview) {
        return new Review(newReview.getIdUser(),
                newReview.getIdBook(),
                newReview.getRating(),
                newReview.getDescription());
    }
}
