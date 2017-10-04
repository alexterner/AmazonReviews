package com.aspectiva.exercise.review.parser.parallel;

import java.util.concurrent.BlockingQueue;

/**
 * Created by aterner on 8/17/2017.
 */
public class Producer implements Runnable {

    private final int numPages;
    private final BlockingQueue queue;

    public Producer(int numPages, BlockingQueue queue) {
        this.numPages = numPages;
        this.queue = queue;
    }


    @Override
    public void run() {
        for (int page = 1 ; page <= numPages; page++) {
            try {
                    queue.put(page);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("Can't add page " + page);
            }
        }
    }
}
