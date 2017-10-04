package com.aspectiva.exercise.page.parser;

import com.aspectiva.exercise.review.parser.CustomerReview;
import com.aspectiva.exercise.review.parser.ParallelReviewParser;
import com.aspectiva.exercise.utils.AsinUtils;
import com.aspectiva.exercise.utils.ElementUtils;
import com.aspectiva.exercise.utils.LoadUtils;
import java.io.IOException;
import java.util.List;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;

/**
 * Created by aterner on 8/23/2017.
 */
public class WebPageParser implements PageParser {


    private Document document;
    private ParallelReviewParser reviewParser;
    private String asin;

    public WebPageParser(String url) throws IOException {
        Validate.notNull(url);

        document = LoadUtils.loadWithRetry(url);
        this.asin = AsinUtils.fromUrl(url);
        this.reviewParser = new ParallelReviewParser(asin);
    }

    public String getItemName() {
        return document != null ? document.title() : "";
    }

    public String getItemRating() {
        return ElementUtils.getAvrRating(document);
    }

    public List<CustomerReview> getReviews() throws IOException {
        return reviewParser.getReviews();
    }

}
