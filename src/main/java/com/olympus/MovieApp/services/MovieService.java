package com.olympus.MovieApp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.olympus.MovieApp.model.*;
import com.olympus.MovieApp.utilities.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Service
public class MovieService {
    Response response;
    GenreMovie genreMovie;
    GenreMovieResponse genreMovieResponse;
    SlackAPI slackAPI;
    RestTemplate restTemplate;

    @Autowired
    MovieService(GenreMovie genreMovie,GenreMovieResponse genreMovieResponse,SlackAPI slackAPI,RestTemplate restTemplate) {
        this.genreMovie = genreMovie;
        this.genreMovieResponse = genreMovieResponse;
        this.slackAPI = slackAPI;
        this.restTemplate = restTemplate;
    }


    public String getGenres() {
        response = getMovies();
        StringBuilder sb = new StringBuilder("Please select your genre --> movies/comedy\n");
        AtomicInteger count = new AtomicInteger(1);
        Set<String> genres = new HashSet<>();
        Stream.of(response.getFeed().getResults()).forEach(movie -> genres.add(movie.getGenres()[0].getName()));
        for(String genre: genres){
            sb.append(count.getAndIncrement() + ". " + genre + "\n");
        }
        return sb.toString();
    }

    public Response getMovies() {
        response = restTemplate.getForObject(
                "https://rss.itunes.apple.com/api/v1/us/movies/top-movies/all/25/explicit.json", Response.class
        );
        return response;
    }

    public List<GenreMovie> getMoviesByGenre(String genre) throws JsonProcessingException {
        response = getMovies();
        List<GenreMovie> movies = new ArrayList<>();
        Stream.of(response.getFeed().getResults()).filter(movie -> movie.getGenres()[0].getName().equalsIgnoreCase(genre))
                .forEach(movie -> {
                    GenreMovie genreMovie = new GenreMovie();
                    genreMovie.setName(movie.getName());
                    genreMovie.setUrl(movie.getUrl());
                    movies.add(genreMovie);
                });
        String text = Utils.parseObject(movies);
        messageSlack(text);
        return movies;
    }

    public void messageSlack(String text) {
        slackAPI.setText(text);
        slackAPI.setChannel("#olympus");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth("xoxb-1001990948439-1123668132240-bpRfe0mC0wlQGsVSdgPNI22j");
        httpHeaders.set("Accept", "application/json");
        HttpEntity<SlackAPI> httpEntity = new HttpEntity<>(slackAPI, httpHeaders);

        String slackURL = "https://slack.com/api/chat.postMessage";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(slackURL, httpEntity, String.class);
        System.out.println("The status code is: " + responseEntity.getStatusCodeValue());
    }
}
