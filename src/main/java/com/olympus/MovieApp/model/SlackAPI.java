package com.olympus.MovieApp.model;

import org.springframework.stereotype.Component;

@Component
public class SlackAPI {

    private String channel;
    private String text;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
