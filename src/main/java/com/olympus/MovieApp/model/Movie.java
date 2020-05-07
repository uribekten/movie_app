package com.olympus.MovieApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

    private String name;
    private String url;
    private List<Genres> genres;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public Genres[] getGenre() {
//        return genres;
//    }
//
//    public void setGenre(Genres[] genre) {
//        this.genres = genre;
//    }
//
//    public Movie(String name, String url, Genres[] genres) {
//        this.name = name;
//        this.url = url;
//        this.genres = genres;
//    }
//
//    @Override
//    public String toString() {
//        return "Movie{" +
//                "name='" + name + '\'' +
//                ", url='" + url + '\'' +
//                ", genres=" + Arrays.toString(genres) +
//                '}';
//    }
}

