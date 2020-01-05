package com.acorn.tutorial.reviewsservice.web;

import com.acorn.tutorial.reviewsservice.model.Review;
import com.acorn.tutorial.reviewsservice.repository.ReviewRepository;
import com.acorn.tutorial.util.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReviewsServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewsServiceController.class);

    private final ReviewRepository reviewRepository;

    private ServiceUtil serviceUtil;

    @Autowired
    public ReviewsServiceController(ReviewRepository reviewRepository, ServiceUtil serviceUtil) {
        this.reviewRepository = reviewRepository;
        this.serviceUtil = serviceUtil;
    }

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
                .orElseGet(Collections::emptyList);

        return reviews.stream()
                .map(this::toReviewDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/reviews/{type}/{typeId}", produces = "application/json")
    public List<ReviewDto> getReviewsForIndividual(@PathVariable String type, @PathVariable Long typeId) {
        List<Review> reviews = reviewRepository.findByTypeAndTypeId(type, typeId)
                .orElseGet(Collections::emptyList);

        return reviews.stream()
                .map(this::toReviewDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/reviews/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewRepository.deleteById(id);
    }

    private ReviewDto toReviewDto(Review review) {
        final ReviewDto reviewDto = ReviewDto.of(review, serviceUtil.getServiceAddress());
        LOGGER.info("Returning {}", reviewDto);
        return reviewDto;
    }
}