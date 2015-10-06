package com.example.jaimequeralt.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jaimequeralt on 9/15/15.
 */
public class Movie implements Parcelable {

    private String title, poster_path, overview, releaseDate;
    private float rating;

    public Movie() {

    }

    public Movie(String title, String poster_path, String overview, String releaseDate, float rating) {
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    public Movie(Parcel in) {
        readFromParcel(in);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster_path);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeFloat(rating);
    }

    private void readFromParcel(Parcel in) {
        title = in.readString();
        poster_path = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        rating = in.readFloat();
    }

    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public Movie createFromParcel(Parcel in) {
                    return new Movie(in);
                }

                public Movie[] newArray(int size) {
                    return new Movie[size];
                }
            };
}
