package com.aspectiva.exercise.review.parser;

import java.io.IOException;

public class AmazonReviewParser extends BaseReviewParser {

    private static final String PREFIX_PRODUCT_REVIEW = "http://www.amazon.com/product-reviews/";
    private static final String SUFFIX_PRODUCT_REVIEW = "/?showViewpoints=0&sortBy=byRankDescending&pageNumber=";

    public AmazonReviewParser(String asin) throws IOException {
        super(asin);
    }

    @Override
    protected String getPageUrl(int pageNumber) {
        return PREFIX_PRODUCT_REVIEW
                + getAsin() +
                SUFFIX_PRODUCT_REVIEW + pageNumber;
    }

}
