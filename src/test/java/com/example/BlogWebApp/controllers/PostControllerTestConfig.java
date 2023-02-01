package com.example.BlogWebApp.controllers;

import com.example.BlogWebApp.entities.Post;
import com.example.BlogWebApp.mappers.PostMapper;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@TestConfiguration
public class PostControllerTestConfig {
    private List<Post> postsList = new ArrayList<>(List.of(
            new Post(2, 1, "some title 1", "some body 1"),
            new Post(3, 2, "some title 2", "some body 2"))
    );

    @Bean
    public PostMapper postMapper() {
        return new PostMapper() {
            @Override
            public Post getPostById(Integer id) {
                for (Post post : postsList)
                    if (post.id == id)
                        return post;

                return null;
            }

            @Override
            public List<Post> getAllPosts() {
                return postsList;
            }

            @Override
            public void insertPost(Post post) {
                postsList.add(post);
            }

            @Override
            public int updatePost(Post post) {
                int count = 0;
                for (Post p : postsList) {
                    if (p.id != post.id)
                        continue;

                    p.userId = post.userId;
                    p.body = post.body;
                    p.title = post.title;
                    count++;
                }

                return count;
            }

            @Override
            public int deletePost(Integer id) {
                int count = 0;
                int indexToDelete = -1;
                for (int i = 0; i < postsList.size(); i++) {
                    if (postsList.get(i).id != id)
                        continue;

                    indexToDelete = i;
                    count++;
                }

                if (count == 1)
                   postsList.remove(indexToDelete);

                return count;
            }
        };
    }
}
