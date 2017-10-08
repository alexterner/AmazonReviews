package com.aspectiva.exercise;

import com.aspectiva.exercise.page.parser.AmazonCrawler;
import com.aspectiva.exercise.page.parser.BaseCrawler;
import com.aspectiva.exercise.page.parser.Crawler;
import com.aspectiva.exercise.review.parser.CustomerReview;
import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {

        long start = System.currentTimeMillis();
//        String url = "https://www.amazon.com/Acer-Chromebook-CB3-131-C3SZ-11-6-Inch-Dual-Core/dp/B019G7VPTC/ref=sr_1_1?s=pc&ie=UTF8&qid=1503474385&sr=1-1-spons&keywords=laptop&psc=1";
//        String url = "https://www.amazon.com/BELK-MacBook-Smooth-Leather-Keyboard-MacBook/dp/B06XCTCKCM/ref=sr_1_21?s=pc&ie=UTF8&qid=1503640301&sr=1-21&keywords=macbook+air+13+inch+case";
        String url = "https://www.amazon.com/Apple-MacBook-13-3-Inch-Laptop-Capitan/dp/B015WXL0C6/ref=zg_bs_565108_18?_encoding=UTF8&psc=1&refRID=4BS32XFT68F23SRJYSNX";
        Crawler crawler = new AmazonCrawler(url);

        System.out.println("Title: " + crawler.getItemName());
        System.out.println("Average Rating: " + crawler.getItemRating());
        List<CustomerReview> reviews = crawler.getReviews();

        System.out.println("Review size: " + reviews.size());
        System.out.println("Takes " + (System.currentTimeMillis() - start) + " ms");

        for ( CustomerReview review: reviews ) {
            System.out.println(review);
        }
    }
}
