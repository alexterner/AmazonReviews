package com.aspectiva.exercise.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by aterner on 8/24/2017.
 */
public enum AsinUtils {
    ;

//    private static final Pattern ASIN_PATTERN = Pattern.compile("/([a-zA-Z0-9]{10})(?:[/?]|$)");
    private static final Pattern ASIN_PATTERN = Pattern.compile("([A-Z0-9]{10})");

    
    public static String fromUrl(String url){
        if(url == null){
           return "";
        }

        Matcher m = ASIN_PATTERN.matcher(url);
        if (m.find( )) {
            return m.group();
        }

        throw new IllegalArgumentException("Can't extract ASIN number from " + url);
    }
}
