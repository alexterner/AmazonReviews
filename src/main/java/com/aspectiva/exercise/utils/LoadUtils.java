package com.aspectiva.exercise.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;

/**
 * Created by aterner on 8/24/2017.
 */
public enum LoadUtils {
    ;

    private static final int DEFAULT_TIMEOUT = 10;
    private static final int DEFAULT_RETRY_INTERVAL = 1;
    private static final int MAX_LOAD_RETRY = 10;

    public static Document loadWithRetry(String url) throws IOException {
        Validate.notNull(url);
        int retry = 1;
        Document document = null;

        while( retry <= MAX_LOAD_RETRY){
            try {
                document = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0")
                        .timeout((int) TimeUnit.SECONDS.toMillis(DEFAULT_TIMEOUT))
                        .get();
                break;
            } catch (Exception ex){
                try {
                    TimeUnit.SECONDS.sleep(DEFAULT_RETRY_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            retry++;
        }
        return document;
    }
}
