package com.acorn.tutorial.webapi.web;

import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;

@FeignClient(name = "reviews-service", fallbackFactory = ReviewsClient.ReviewsServiceFallbackFactory.class)
public interface ReviewsClient {

    @GetMapping("/reviews/{type}")
    List<Review> getReviews(@PathVariable String type);

    @GetMapping("/reviews/{type}/{typeid}")
    List<Review> getReviews(@PathVariable String type, @PathVariable Long typeid);

    @Component
    class ReviewsServiceFallbackFactory implements FallbackFactory<ReviewsClient> {

        @Override
        public ReviewsClient create(Throwable throwable) {
            return new ReviewsClient() {
                @Override
                public List<Review> getReviews(String type) {
                    return Collections.emptyList();
                }

                @Override
                public List<Review> getReviews(String type, Long typeid) {
                    return null;
                }
            };
        }
    }
}