package com.aspectiva.exercise.review.parser;

import com.aspectiva.exercise.utils.ElementUtils;
import com.aspectiva.exercise.utils.LoadUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by aterner on 8/23/2017.
 */
public class OnePageReviewParser implements ReviewParser {

    private static final String REVIEW = ".review";

    private static final int MAX_NUM_REVIEWS_IN_PAGE = 10;
    private static final String PREFIX_PRODUCT_REVIEW = "http://www.amazon.com/product-reviews/";
    private static final String SUFFIX_PRODUCT_REVIEW = "/?showViewpoints=0&sortBy=byRankDescending&pageNumber=";
//    private static final String SUFFIX_PRODUCT_REVIEW = "/?showViewpoints=0&sortBy=byDateDescending&pageNumber=";

    private Document document;
    private String asin;
    private int pageNumber;

    public OnePageReviewParser(String asin, int pageNumber) throws IOException{
        this.asin = asin;
        this.pageNumber = pageNumber;
        connect( getPageUrl() );
    }

    private String getPageUrl(){
        return PREFIX_PRODUCT_REVIEW + asin +
               SUFFIX_PRODUCT_REVIEW + pageNumber;
    }

    private void connect(String pageReviewUrl) throws IOException {
        this.document = LoadUtils.loadWithRetry(pageReviewUrl);
    }


    public List<CustomerReview> getReviews() {

        List<CustomerReview> customerReviews = new ArrayList<>( MAX_NUM_REVIEWS_IN_PAGE);

//            System.out.println("Page : " + pageNumber);

            Elements reviewElements = document.select(REVIEW);
            if (reviewElements == null || reviewElements.isEmpty()) {
                return Collections.emptyList();
            }

            for (Element reviewElement : reviewElements) {

                CustomerReview customerReview = new CustomerReview();

                String title = ElementUtils.getTitle(reviewElement);
                if (StringUtils.isNoneBlank( title) ) {
//                    System.out.println("Title: " + title);
                    customerReview.setTitle(title);
                }

                String author = ElementUtils.getAuthor(reviewElement);
                if (StringUtils.isNoneBlank( author) ) {
//                    System.out.println("Author: " + author);
                    customerReview.setUser(author);
                }

                String text = ElementUtils.getText(reviewElement);
                if (StringUtils.isNoneBlank( text) ) {
//                    System.out.println("Text: " + text);
                    customerReview.setText(text);
                }

                String rating = ElementUtils.getRating(reviewElement);
                if(StringUtils.isNoneBlank(rating)){
//                    System.out.println("Rating: " + rating);
                    customerReview.setRating(rating);
                }

                String date = ElementUtils.getDate(reviewElement);
                if(StringUtils.isNoneBlank(date)){
//                    System.out.println("Date: " + date);
                    customerReview.setDate(date);
                }

                customerReviews.add(customerReview);

//                System.out.println("-------------------------------------------");
            }

        return customerReviews;
    }
}
