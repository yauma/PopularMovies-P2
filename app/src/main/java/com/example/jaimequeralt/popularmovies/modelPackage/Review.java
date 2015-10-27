package com.example.jaimequeralt.popularmovies.modelPackage;

/**
 * Created by jaimequeralt on 10/27/15.
 */
public class Review {

    String author,review;

    public Review(String author, String review) {
        this.author = author;
        this.review = review;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

}
