package com.aspectiva.exercise.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by aterner on 8/24/2017.
 */
public class AsinUtilsTest {


    @Test
    public void fromUrl() throws Exception {
        String url = "https://www.amazon.com/Acer-Chromebook-CB3-131-C3SZ-11-6-Inch-Dual-Core/dp/B019G7VPTC/ref=sr_1_1?s=pc&ie=UTF8&qid=1503474385&sr=1-1-spons&keywords=laptop&psc=1";

        String actual = AsinUtils.fromUrl(url);
        String expected = "B019G7VPTC";

        Assert.assertEquals("Incorrect ASIN number",expected, actual);
    }

}