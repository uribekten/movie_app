package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response implements Serializable {

    private Feed feed;

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public Response(Feed feed) {
        this.feed = feed;
    }
}
