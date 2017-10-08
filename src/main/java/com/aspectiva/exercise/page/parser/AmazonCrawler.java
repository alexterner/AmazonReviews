package com.aspectiva.exercise.page.parser;

import com.aspectiva.exercise.review.parser.AmazonReviewParser;
import com.aspectiva.exercise.utils.AsinUtils;

import java.io.IOException;

public class AmazonCrawler extends BaseCrawler {

    public AmazonCrawler(String url) throws IOException {
        super(url, new AmazonReviewParser( AsinUtils.fromUrl(url)) );
    }

}
