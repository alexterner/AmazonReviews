package com.aspectiva.exercise.review.parser;

/**
 * Created by aterner on 8/24/2017.
 */
public class CustomerReview {

    private String title;
    private String user;
    private String text;
    private String rating;
    private String date;


    public CustomerReview() {
        this.title = "";
        this.user = "";
        this.text = "";
        this.rating = "";
        this.date = "";
    }

    public CustomerReview(String user, String rating, String title, String text, String date) {
        this.title = title;
        this.user = user;
        this.text = text;
        this.rating = rating;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override public String toString() {
        return "CustomerReview{" +
               "user='" + user + '\'' +
               ", rating='" + rating + '\'' +
               ", date='" + date + '\'' +
               ", title='" + title + '\'' +
               ", text='" + text + '\'' +
               '}';
    }
}
