package com.aspectiva.exercise;

import com.aspectiva.exercise.review.parser.CustomerReview;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReviewReport {

    private String itemName;
    private String itemRating;
    private List<CustomerReview> reviews;
}
