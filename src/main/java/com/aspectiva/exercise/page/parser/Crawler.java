package com.aspectiva.exercise.page.parser;

import com.aspectiva.exercise.review.parser.CustomerReview;

import java.io.IOException;
import java.util.List;

/**
 * Created by aterner on 8/23/2017.
 */
public interface Crawler {

    String getItemName();

    String getItemRating();

    List<CustomerReview> getReviews() throws IOException;
    
}
