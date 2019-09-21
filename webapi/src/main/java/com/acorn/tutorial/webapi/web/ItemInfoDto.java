package com.acorn.tutorial.webapi.web;

import java.util.List;

import lombok.ToString;
import lombok.Value;

@Value(staticConstructor = "of")
@ToString
public class ItemInfoDto {

    private Item item;
    private List<Review> reviews;
}