package com.acorn.tutorial.reviewsservice.repository;

import com.acorn.tutorial.reviewsservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<List<Review>> findByType(String type);
    Optional<List<Review>> findByTypeAndTypeId(String type, Long typeId);
}