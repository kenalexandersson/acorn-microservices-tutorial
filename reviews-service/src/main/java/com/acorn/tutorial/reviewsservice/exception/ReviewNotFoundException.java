package com.acorn.tutorial.reviewsservice.exception;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(String type, Long typeId) {
        super(String.format("Failed to find review of type %s with typeId: %d", type, typeId));
    }
}