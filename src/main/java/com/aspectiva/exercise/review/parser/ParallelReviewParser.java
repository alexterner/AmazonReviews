package com.aspectiva.exercise.review.parser;

import com.aspectiva.exercise.review.parser.parallel.Consumer;
import com.aspectiva.exercise.review.parser.parallel.Producer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by aterner on 8/23/2017.
 */
public class ParallelReviewParser implements Review {

    private static final String PREFIX_PRODUCT_REVIEW = "http://www.amazon.com/product-reviews/";
    private static final String SUFFIX_PRODUCT_REVIEW = "/?showViewpoints=0&sortBy=byRankDescending&pageNumber=";

    private final static int NUM_THREADS = 5;

    private Document document;
    private String asin;
    private BlockingQueue queue;
    private ExecutorService executorService;
    private int totalPages;

    public ParallelReviewParser(String asin) throws IOException{
        this.asin = asin;
        this.executorService = Executors.newFixedThreadPool(NUM_THREADS);
        this.queue = new LinkedBlockingQueue();
        connect( getPageUrl(1) );
        calculateNumPages();
    }

    private String getPageUrl(int pageNumber){
        return PREFIX_PRODUCT_REVIEW
               + asin +
               SUFFIX_PRODUCT_REVIEW + pageNumber;
    }

    private void connect(String pageReviewUrl) throws IOException {
        this.document = Jsoup.connect(pageReviewUrl).get();
    }

    private void calculateNumPages() {
        Elements pagelinks = document.select("a[href*=pageNumber=]");
        if (pagelinks.size() != 0) {
            ArrayList<Integer> pagenum = new ArrayList<Integer>();
            for (Element link : pagelinks) {
                try {
                    pagenum.add(Integer.parseInt(link.text()));
                } catch (NumberFormatException nfe) {
                }
            }
            this.totalPages = Collections.max(pagenum);
            System.out.println("Num Pages: " + totalPages);
        }
    }


    public List<CustomerReview> getReviews()  {

        executorService.execute(new Producer(totalPages, queue));

        List<Future<List<CustomerReview>>> consumers = new ArrayList<>();

        Callable<List<CustomerReview>> consumer = new Consumer(asin, queue);

        for(int i=0; i< totalPages; i++){
            Future<List<CustomerReview>> future = executorService.submit(consumer);
            consumers.add(future);
        }

        List<CustomerReview> customerReviews = new LinkedList<>();
        for(Future<List<CustomerReview>> fut : consumers){
            try {
                List<CustomerReview> reviews = fut.get();
                    customerReviews.addAll(reviews);
                    System.out.println("Completed reviews : " + customerReviews.size());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
        return  customerReviews;
    }
}
