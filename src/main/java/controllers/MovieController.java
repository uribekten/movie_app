package controllers;

import model.Feed;
import model.Genre;
import model.Movie;
import model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    RestTemplate restTemplate;


    @GetMapping("/comedies")
    public Response getMovies(){
        Response response = restTemplate.getForObject(
                "https://rss.itunes.apple.com/api/v1/us/movies/top-movies/all/25/explicit.json", Response.class
        );
        Movie[] movies = response.getFeed().getResults();
        System.out.println(movies[0].getName());
        return response;
    }
}
