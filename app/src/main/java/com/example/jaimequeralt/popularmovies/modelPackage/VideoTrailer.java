package com.example.jaimequeralt.popularmovies.modelPackage;

/**
 * Created by jaimequeralt on 10/25/15.
 */
public class VideoTrailer {

    private String key,name;

    public VideoTrailer() {
    }

    public VideoTrailer(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
