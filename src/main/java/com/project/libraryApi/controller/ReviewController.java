package com.project.libraryApi.controller;

import com.project.libraryApi.dto.ReviewDTO;
import com.project.libraryApi.model.Review;
import com.project.libraryApi.service.ReviewService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {
    Logger log = LoggerFactory.getLogger(ReviewController.class);
    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> listAllReviews() {
        log.info("Receive request to GET ALL reviews");
        return new ResponseEntity<>(reviewService.getAllReviews(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        log.info("Receive request to GET review. id: ".concat(id.toString()));
        Review review = reviewService.getReviewById(id);
        log.info("Review find success: ".concat(review.toString()));
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Review> saveReview(@Valid @RequestBody ReviewDTO review) {
        log.info("Receive request to INSERT review: ".concat(review.toString()));
        return new ResponseEntity<>(reviewService.saveReview(review), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @Valid @RequestBody ReviewDTO review) {
        log.info("Receive request to UPDATE review. id: ".concat(id.toString()).concat(" review: "
                .concat(review.toString())));
        return new ResponseEntity<>(reviewService.updateReview(id, review), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Review> deleteReview(@PathVariable Long id) {
        log.info("Receive request to DELETE review. id: ".concat(id.toString()));
        reviewService.deleteReview(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
