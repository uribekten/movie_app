package com.olympus.MovieApp.controllers;

import com.olympus.MovieApp.model.Movie;
import com.olympus.MovieApp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    RestTemplate restTemplate;


    @GetMapping("/comedies")
    public String getMovies(){
        Response response = restTemplate.getForObject(
                "https://rss.itunes.apple.com/api/v1/us/movies/top-movies/all/25/explicit.json", Response.class
        );
        StringBuilder sb = new StringBuilder();
//        Movie[] movies = response.getFeed().getResults();
//        System.out.println(movies[0].getName());
        response.getFeed().getResults().stream()
                .filter(result ->result.getGenres().get(0).getName().equalsIgnoreCase("comedy") )
                .forEach(result -> sb.append(result.getName() + " and URL " + result.getUrl() +"\n"));

        return sb.toString();
    }
}
