package com.acorn.tutorial.webapi.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Review {

    private Long id;

    private String type;

    private Long typeId;

    private Integer rating;

    private Integer ratingMin;

    private Integer ratingMax;

    private String comment;

    private String serviceAddress;
}