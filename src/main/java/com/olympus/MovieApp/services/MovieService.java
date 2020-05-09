package com.olympus.MovieApp.services;

import com.olympus.MovieApp.model.*;
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

    public String getComediesString() {
        response = getMovies();

        StringBuilder str = new StringBuilder("Top comedy movies are: \n");
        AtomicInteger count = new AtomicInteger(1);
        Stream.of(response.getFeed().getResults())
                .filter(movie -> movie.getGenres()[0].getName().equalsIgnoreCase("Comedy"))
                .forEach(movie -> str.append(count.getAndIncrement() + ". " + movie.getName() + "\n"));
        String message = str.toString();
        return message;
    }

    public List<GenreMovie> getMoviesByGenre(String genre) {
        response = getMovies();
        List<GenreMovie> movies = new ArrayList<>();
        Stream.of(response.getFeed().getResults()).filter(movie -> movie.getGenres()[0].getName().equalsIgnoreCase(genre))
                .forEach(movie -> {
                    GenreMovie genreMovie = new GenreMovie();
                    genreMovie.setName(movie.getName());
                    genreMovie.setUrl(movie.getUrl());
                    movies.add(genreMovie);
                });
        return movies;
    }

    public int messageSlack() {
        String message = getComediesString();

        slackAPI.setText(message);
        slackAPI.setChannel("#olympus");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth("xoxb-1001990948439-1123668132240-bpRfe0mC0wlQGsVSdgPNI22j");
        httpHeaders.set("Accept", "application/json");
        HttpEntity<SlackAPI> httpEntity = new HttpEntity<>(slackAPI, httpHeaders);

        String slackURL = "https://slack.com/api/chat.postMessage";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(slackURL, httpEntity, String.class);
        return responseEntity.getStatusCodeValue();
    }

//    public int messageSlackJson() throws JsonProcessingException {
//        genreMovieResponse = getMoviesByGenre();
//        ObjectMapper objectMapper = new ObjectMapper();
//        String message = objectMapper.writeValueAsString(genreMovieResponse);
//
//        slackAPI.setText(message);
//        slackAPI.setChannel("#olympus");
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        httpHeaders.setBearerAuth("xoxb-1001990948439-1123668132240-bpRfe0mC0wlQGsVSdgPNI22j");
//        httpHeaders.set("Accept", "application/json");
//        HttpEntity<SlackAPI> httpEntity = new HttpEntity<>(slackAPI, httpHeaders);
//
//        String slackURL = "https://slack.com/api/chat.postMessage";
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(slackURL, httpEntity, String.class);
//        return responseEntity.getStatusCodeValue();
//    }

}
