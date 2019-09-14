package com.acorn.tutorial.reviewsservice.web;

import com.acorn.tutorial.reviewsservice.model.Review;
import lombok.Data;

@Data
public class ReviewDto {

    private Long id;

    private String type;

    private Long typeId;

    private Integer rating;

    private Integer ratingMin;

    private Integer ratingMax;

    private String comment;

    private int port;

    public static ReviewDto of(Review review, int port) {
        return new ReviewDto(review, port);
    }

    private ReviewDto(Review review, int port) {
        this.id = review.getId();
        this.type = review.getType();
        this.typeId = review.getTypeId();
        this.rating = review.getRating();
        this.ratingMin = review.getRatingMin();
        this.ratingMax = review.getRatingMax();
        this.comment = review.getComment();
        this.port = port;
    }
}