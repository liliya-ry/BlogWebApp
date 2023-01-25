package com.example.BlogWebApp.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Post {
    public int id;
    public int userId;
    public String title;
    public String body;

    public Post(@JsonProperty("userId") int userId, @JsonProperty("title") String title, @JsonProperty("body") String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }
}
