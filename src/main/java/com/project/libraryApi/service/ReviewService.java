package com.project.libraryApi.service;

import com.project.libraryApi.dto.ReviewDTO;
import com.project.libraryApi.exception.ReviewNotFoundException;
import com.project.libraryApi.mapper.ReviewMapper;
import com.project.libraryApi.model.Review;
import com.project.libraryApi.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"reviews"})
public class ReviewService {
    Logger log = LoggerFactory.getLogger(ReviewService.class);
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @Cacheable("reviewsList")
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public Review getReviewById(Long id) {
        log.info("Finding review. id: ".concat(id.toString()));
        return reviewRepository
                .findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
    }

    @Caching(evict = @CacheEvict(cacheNames = "reviewsList", allEntries = true),
            put = @CachePut(key = "#result.id", unless = "#result == null"))
    public Review saveReview(ReviewDTO review) {
        log.info("Saving review. ".concat(review.toString()));
        if (validateReview(review)) {
            Review saveReview = reviewRepository.save(ReviewMapper.toReview(review));
            log.info("Review saved success: ".concat(saveReview.toString()));
            return saveReview;
        }
        return null;
    }

    public boolean validateReview(ReviewDTO review) {
        return bookService.getBookById(review.getIdBook()) != null
                && userService.getUserById(review.getIdUser()) != null;
    }

    @CacheEvict(key = "#id")
    @Caching(evict = {@CacheEvict(cacheNames = "reviewsList", allEntries = true), @CacheEvict(key = "#id")})
    public void deleteReview(Long id) {
        log.info("Deleting review. id: ".concat(id.toString()));
        reviewRepository.deleteById(id);
        log.info("Review deleted success: id ".concat(id.toString()));

    }

    @Caching(evict = @CacheEvict(cacheNames = "reviewsList", allEntries = true),
            put = @CachePut(key = "#result.id", unless = "#result == null"))
    public Review updateReview(Long id, ReviewDTO review) {
        log.info("Updating review. id: ".concat(id.toString()).concat(" new data: ".concat(review.toString())));
        Review originalReview = reviewRepository
                .findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
        if (validateReview(review)) {
            Review saveReview = reviewRepository.save(ReviewMapper.toReview(originalReview, review));
            log.info("User updated success: id ".concat(id.toString()));
            return saveReview;
        }
        return originalReview;
    }

}
