package com.acorn.tutorial.webapi.web;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebApiController {

    private final ItemsClient itemsClient;
    private final ReviewsClient reviewsClient;

    @Autowired
    public WebApiController(ItemsClient itemsClient, ReviewsClient reviewsClient) {
        this.itemsClient = itemsClient;
        this.reviewsClient = reviewsClient;
    }

    @GetMapping(path = "/webapi/items")
    public List<ItemInfoDto> getItems() {

        List<Item> items = itemsClient.getItems();
        List<Review> reviews = reviewsClient.getReviews("item");

        return items.stream()
                .map(item -> createItemInfoDto(item, reviews))
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/webapi/items/{id}")
    public ItemInfoDto getItem(@PathVariable Long id) {

        Item item = itemsClient.getItem(id);
        List<Review> reviews = reviewsClient.getReviews("item", id);

        return ItemInfoDto.of(item, reviews);
    }

    private ItemInfoDto createItemInfoDto(Item item, List<Review> reviews) {

        List<Review> itemReviews = reviews.stream()
                .filter(review -> item.getId().equals(review.getTypeId()))
                .collect(Collectors.toList());

        return ItemInfoDto.of(item, itemReviews);
    }
}
