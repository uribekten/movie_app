package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

    private String name;
    private String url;
    private Genre[] genres;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Genre[] getGenre() {
        return genres;
    }

    public void setGenre(Genre[] genre) {
        this.genres = genre;
    }

    public Movie(String name, String url, Genre[] genres) {
        this.name = name;
        this.url = url;
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", genres=" + Arrays.toString(genres) +
                '}';
    }
}

