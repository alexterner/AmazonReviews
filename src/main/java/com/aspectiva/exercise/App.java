package com.aspectiva.exercise;

import com.aspectiva.exercise.page.parser.AmazonCrawler;
import com.aspectiva.exercise.page.parser.Crawler;
import com.aspectiva.exercise.review.parser.CustomerReview;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {

        long start = System.currentTimeMillis();
        String url1 = "https://www.amazon.com/Acer-Chromebook-CB3-131-C3SZ-11-6-Inch-Dual-Core/dp/B019G7VPTC/ref=sr_1_1?s=pc&ie=UTF8&qid=1503474385&sr=1-1-spons&keywords=laptop&psc=1";
        String url2 = "https://www.amazon.com/BELK-MacBook-Smooth-Leather-Keyboard-MacBook/dp/B06XCTCKCM/ref=sr_1_21?s=pc&ie=UTF8&qid=1503640301&sr=1-21&keywords=macbook+air+13+inch+case";
        String url3 = "https://www.amazon.com/Apple-MacBook-13-3-Inch-Laptop-Capitan/dp/B015WXL0C6/ref=zg_bs_565108_18?_encoding=UTF8&psc=1&refRID=4BS32XFT68F23SRJYSNX";
        String url4 = "https://www.amazon.com/Mosiso-MacBook-Release-Plastic-Protector/dp/B01M4R9VMF/ref=sr_1_3?ie=UTF8&qid=1508354150&sr=8-3&keywords=macbook+pro+13+inch+2017";
        String url5 = "https://www.amazon.com/Apple-MacBook-Display-MPXT2LL-Version/dp/B072QGNFMC/ref=sr_1_5?ie=UTF8&qid=1508354150&sr=8-5&keywords=macbook+pro+13+inch+2017";
        String url6 = "https://www.amazon.com/Kuzy-MacBook-Rubberized-Release-without/dp/B01MQK5TH3/ref=sr_1_6?ie=UTF8&qid=1508354150&sr=8-6&keywords=macbook+pro+13+inch+2017";


        String amazonUrl1 = "https://www.amazon.com/BLU-R1-Cell-Phone-16GB/dp/B01H2E0KVA/ref=lp_2407749011_1_11?s=wireless&ie=UTF8&qid=1510339085&sr=1-11";
        String amazonUrl2 = "https://www.amazon.com/Apple-iPhone-Factory-Unlocked-Phone/dp/B00NQGP42Y/ref=lp_2407749011_1_24?s=wireless&ie=UTF8&qid=1510339085&sr=1-24";
        String amazonUrl3 = "https://www.amazon.com/dp/B00YD546IA/ref=sr_1_36_twi_col_ti_3?s=wireless&amp;ie=UTF8&amp;qid=1510339120&amp;sr=1-36";
        String amazonUrl4 = "https://www.amazon.com/Apple-Iphone-5s-16GB-Unlocked/dp/B00F3J4B5S/ref=sr_1_43?s=wireless&ie=UTF8&qid=1510339120&sr=1-43";
        String amazonUrl5 = "https://www.amazon.com/OontZ-Angle-Bluetooth-Proprietary-SoundWorks/dp/B01LZV6TYQ/ref=sr_1_1_sspa?s=electronics&ie=UTF8&qid=1510341080&sr=1-1-spons&keywords=sony+bluetooth+speaker&psc=1";
//https://www.amazon.com/AT-LP60/product-reviews/B008872SIO/ref=cm_cr_getr_d_paging_btm_1?ie=UTF8&reviewerType=all_reviews&sortBy=recent&pageNumber=1


//        List<String> urls = Lists.newArrayList(url1, url2/*, url3, url4, url5, url6*/);

        List<String> urls = Lists.newArrayList(amazonUrl1, amazonUrl2, amazonUrl3, amazonUrl4, amazonUrl5);
        ExecutorService executor = Executors.newFixedThreadPool( urls.size());

        List<CompletableFuture<Crawler>> crawlers = urls.stream()
//        .map(crawlerReport -> CompletableFuture.runAsync( () -> crawlerReport.runCrawler(), executor) );
//                .map(url -> CompletableFuture.runAsync(() -> runCrawler(url), executor));
                .map( url -> CompletableFuture.supplyAsync(() -> runCrawler(url), executor))
                  .map( c -> c.thenApply(App::crawlerTest))
                  .collect(Collectors.toList());




//        runCrawler(url1);

    /*    crawlers.stream()
                .map(CompletableFuture::join);*/
        System.out.println("----- Total time: " + (System.currentTimeMillis() - start));
    }


    private static CompletableFuture<Crawler> startCrawler(String url, ExecutorService executor){
        return CompletableFuture.supplyAsync(() -> runCrawler(url), executor)
                .exceptionally(e -> {
                     System.err.println("Error on starting crawler by url: " + url + ", error message" + e.getMessage());
                    return null;
                });
    }

    private static Crawler crawlerTest(Crawler crawler)  {
        System.out.println(crawler.getItemName() + " Done.");
        return crawler;
    }

    private static Crawler runCrawler(String url)  {
        Crawler crawler = null;
        try {
            crawler = new AmazonCrawler(url);

                System.out.println("Title: " + crawler.getItemName());
                System.out.println("Average Rating: " + crawler.getItemRating());
                List<CustomerReview> reviews = crawler.getReviews();

                System.out.println("ReviewReport size: " + reviews.size());

                for (CustomerReview review : reviews) {
                    System.out.println(review);
                }

            }catch (IOException e){
                e.printStackTrace();
            }
            return crawler;

    }
}
