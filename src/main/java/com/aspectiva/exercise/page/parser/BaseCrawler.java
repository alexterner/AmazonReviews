package com.aspectiva.exercise.page.parser;

import com.aspectiva.exercise.review.parser.CustomerReview;
import com.aspectiva.exercise.review.parser.ReviewParser;
import com.aspectiva.exercise.utils.ElementUtils;
import com.aspectiva.exercise.utils.LoadUtils;
import java.io.IOException;
import java.util.List;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;

/**
 * Created by aterner on 8/23/2017.
 */
public abstract class BaseCrawler implements Crawler {


    private Document document;
    private ReviewParser reviewParserParser;

    public BaseCrawler(String url, ReviewParser reviewParserParser) throws IOException {
        Validate.notNull(url);
        document = LoadUtils.loadWithRetry(url);
        this.reviewParserParser = reviewParserParser;
    }

    public String getItemName() {
        return document != null ? document.title() : "";
    }

    public String getItemRating() {
        return ElementUtils.getAvrRating(document);
    }

    public List<CustomerReview> getReviews() throws IOException {
        return reviewParserParser.getReviews();
    }

}
