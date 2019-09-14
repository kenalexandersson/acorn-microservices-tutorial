package com.acorn.tutorial.reviewsservice.web;

import com.acorn.tutorial.reviewsservice.exception.ReviewNotFoundException;
import com.acorn.tutorial.reviewsservice.model.Review;
import com.acorn.tutorial.reviewsservice.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReviewsServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewsServiceController.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private Environment environment;

    @GetMapping(path = "/reviews", produces = "application/json")
    public List<ReviewDto> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::toReviewDto)
                .collect(Collectors.toList());
    }

    @PostMapping(path = "/reviews")
    public ReviewDto addReview(@RequestBody Review newReview) {
        return toReviewDto(reviewRepository.save(newReview));
    }

    @GetMapping(path = "/reviews/{type}", produces = "application/json")
    public List<ReviewDto> getReviews(@PathVariable String type) {
        List<Review> reviews = reviewRepository.findByType(type)
                .orElseThrow(() -> new ReviewNotFoundException(type, null));

        return reviews.stream()
                .map(this::toReviewDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/reviews/{type}/{typeId}", produces = "application/json")
    public List<ReviewDto> getReviewsForIndividual(@PathVariable String type, @PathVariable Long typeId) {
        List<Review> reviews = reviewRepository.findByTypeAndTypeId(type, typeId)
                .orElseThrow(() -> new ReviewNotFoundException(type, typeId));

        return reviews.stream()
                .map(this::toReviewDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/reviews/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewRepository.deleteById(id);
    }

    private ReviewDto toReviewDto(Review review) {
        int port = Integer.parseInt(environment.getProperty("local.server.port", "0"));
        final ReviewDto reviewDto = ReviewDto.of(review, port);
        LOGGER.info(String.format("Returning %s", reviewDto));
        return reviewDto;
    }
}