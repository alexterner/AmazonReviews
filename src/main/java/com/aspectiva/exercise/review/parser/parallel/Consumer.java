package com.aspectiva.exercise.review.parser.parallel;

import com.aspectiva.exercise.review.parser.CustomerReview;
import com.aspectiva.exercise.review.parser.OnePageReviewParser;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;


/**
 * Created by aterner on 8/17/2017.
 */
public class Consumer implements Callable<List<CustomerReview>> {

    private final BlockingQueue queue;
    private String asin;


    public Consumer(String asin, BlockingQueue queue) {
        this.queue = queue;
        this.asin = asin;

    }

    @Override
    public List<CustomerReview> call() throws Exception {
        while (true) {
            int page = 0;
            try {
                page = (int) queue.take();
                OnePageReviewParser onePageReviewParser = new OnePageReviewParser(asin, page);
                return onePageReviewParser.getReviews();

            } catch (InterruptedException ex) {
                System.err.println("Interrupted exception in page " + page);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return Collections.emptyList();
        }
    }
}
