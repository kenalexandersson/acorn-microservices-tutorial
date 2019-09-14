package com.acorn.tutorial.reviewsservice.web;

import com.acorn.tutorial.reviewsservice.exception.ReviewNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ReviewNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ReviewNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String itemNotFoundHandler(ReviewNotFoundException ex) {
        return ex.getMessage();
    }
}