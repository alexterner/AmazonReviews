package com.aspectiva.exercise.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by aterner on 8/24/2017.
 */
public class ElementUtils {
    ;

    private static final String REVIEW_RATING = "i.a-icon-star";
    private static final String AVR_CUSTOMER_REVIEW = "averageCustomerReviews";
    private static final String REVIEW_TITLE = ".review-title";
    private static final String REVIEW_TEXT = ".review-text";
    private static final String REVIEW_DATE = "span.review-date";

    private static final Pattern AUTHOR_PATTERN = Pattern.compile("(/gp/profile/)(.+)(/)");

    public static String getRating(Element reviewElement) {

        Element starElement = reviewElement.select(REVIEW_RATING).first();
        if (starElement != null) {
            String rating = starElement.text();
            return rating.substring(0, rating.indexOf("out")).trim();
        }

        return "";
    }

    public static String getAvrRating(Element reviewElement) {

        Element avrStarElement = reviewElement.getElementById(AVR_CUSTOMER_REVIEW);
        return getRating(avrStarElement);
    }

    public static String getTitle(Element reviewElement) {
        Element titleElement = reviewElement.select(REVIEW_TITLE).first();
        if (titleElement != null) {
            String title = titleElement.text();
            return title;
        }
        return "";
    }

    public static String getText(Element reviewElement) {
        Element textElement = reviewElement.select(REVIEW_TEXT).first();
        if (textElement != null) {
            String text = textElement.text();
            return text;
        }
        return "";
    }

    public static String getDate(Element reviewElement) {
        Element dateElement = reviewElement.select(REVIEW_DATE).first();
        if (dateElement != null) {
            String date = dateElement.text();
            return date.substring(3);
        }
        return "";
    }


    public static String getAuthor(Element reviewBlock) {

        Elements customerElement = reviewBlock.getElementsByAttributeValueContaining("href", "/gp/profile/");
        if (customerElement.size() > 0) {
            Element customer = customerElement.first();
            String customerhref = customer.attr("href");
            Matcher matcher = AUTHOR_PATTERN.matcher(customerhref);
            while(matcher.find()) {
                return customer.text();
            }
        }
        return "";
    }
}
