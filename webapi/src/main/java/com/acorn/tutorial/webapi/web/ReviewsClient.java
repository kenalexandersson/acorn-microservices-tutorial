package com.acorn.tutorial.webapi.web;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "reviews-service")
public interface ReviewsClient {

    @GetMapping("/reviews/{type}")
    List<Review> getReviews(@PathVariable String type);

    @GetMapping("/reviews/{type}/{typeid}")
    List<Review> getReviews(@PathVariable String type, @PathVariable Long typeid);
}