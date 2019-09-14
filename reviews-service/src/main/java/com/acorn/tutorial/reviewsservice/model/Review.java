package com.acorn.tutorial.reviewsservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Review {

    @Id
    private Long id;

    private String type;

    @Column(name = "type_id")
    private Long typeId;

    private Integer rating;

    @Column(name = "rating_min")
    private Integer ratingMin;

    @Column(name = "rating_max")
    private Integer ratingMax;

    private String comment;
}
