package com.aspectiva.exercise;

import com.aspectiva.exercise.page.parser.AmazonCrawler;
import com.aspectiva.exercise.page.parser.Crawler;
import com.aspectiva.exercise.review.parser.CustomerReview;

import java.io.IOException;
import java.util.List;

public class CrawlerReport {

    private String url;

    public CrawlerReport(String url) {
        this.url = url;
    }

    public void runCrawler()  {
        try {
            Crawler crawler = new AmazonCrawler(url);

            System.out.println("Title: " + crawler.getItemName());
            System.out.println("Average Rating: " + crawler.getItemRating());
            List<CustomerReview> reviews = crawler.getReviews();

            System.out.println("ReviewReport size: " + reviews.size());

            for (CustomerReview review : reviews) {
                System.out.println(review);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
