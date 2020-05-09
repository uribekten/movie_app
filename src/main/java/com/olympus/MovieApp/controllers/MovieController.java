package com.olympus.MovieApp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.olympus.MovieApp.model.*;
import com.olympus.MovieApp.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    MovieService movieService;

    @Autowired
    MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @GetMapping("")
    public String getGenresList() {
        return movieService.getGenres();
    }

    @GetMapping("all")
    public Response getAllMovies(){
        return movieService.getMovies();
    }

    @GetMapping("/comediesString")
    public String getComedies(){
        return movieService.getComediesString();
    }

    @GetMapping("/{genre}")
    public List<GenreMovie> getComediesJson(@PathVariable String genre) {
        return movieService.getMoviesByGenre(genre);
    }

    @GetMapping("/messageJson")
    public int messageSlackJson() throws JsonProcessingException {
        return 0;
    }

    @GetMapping("/message")
    public int messageSlack(){
        return movieService.messageSlack();
    }
}
