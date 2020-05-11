package com.olympus.MovieApp.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GenreMovieResponse {

    private List<GenreMovie> genreMovies;

    public List<GenreMovie> getGenreMovies() {
        return genreMovies;
    }

    public void setGenreMovies(List<GenreMovie> genreMovies) {
        this.genreMovies = genreMovies;
    }
}
