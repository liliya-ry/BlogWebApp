package com.example.BlogWebApp.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.BlogWebApp.entities.*;
import com.example.BlogWebApp.exceptions.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import java.util.*;

@SpringBootTest
@Import({PostControllerTestConfig.class, PostControllerImpl.class})
class PostControllerTest {
    @Autowired
    private PostControllerImpl postController;

    @Test
    void getAllPosts() {
        Post post1 = new Post(2, 1, "some title 1", "some body 1");
        Post post2 = new Post(3, 2, "some title 2", "some body 2");
        List<Post> expectedResultList = List.of(post1, post2);
        List<Post> actualResultList = postController.getAllPosts();
        assertThat(actualResultList, is(expectedResultList));
    }

    @Test
    void getExistingPost() throws JsonProcessingException {
        Post expectedPost = new Post(2, 1, "some title 1", "some body 1");
        Post actualPost = (Post) postController.getPostById(2);
        assertThat(expectedPost, is(actualPost));
    }

    @Test
    void getNonExistingPost() {
        assertThrows(NotFoundException.class, () -> postController.getPostById(1));
    }

    @Test
    void addPost() throws JsonProcessingException {
        Post post = new Post(1, 1, "st", "sb");
        postController.createPost(post);
        assertThat(postController.getAllPosts(), hasSize(3));
        assertThat(postController.getPostById(1), is(post));
    }

    @Test
    void updateExistingPost() throws JsonProcessingException {
        Post post = new Post(2, 1, "new title", "sb");
        postController.updatePost(post, 2);
        Post updatedPost = (Post) postController.getPostById(2);
        assertThat(updatedPost.title, is("new title"));
    }

    @Test
    void updateNonExistingPost() {
        Post post = new Post(1, 1, "new title", "sb");
        assertThrows(NotFoundException.class, () -> postController.updatePost(post, 1));
    }

    @Test
    void deleteExistingPost() {
        List<Post> postList = postController.getAllPosts();
        System.out.println(postList.size());
        assertDoesNotThrow(() -> postController.deletePost(2));
        assertThrows(NotFoundException.class, () -> postController.getPostById(2));
        assertThat(postController.getAllPosts(), hasSize(postList.size()));
    }

    @Test
    void deleteNonExistingPost() {
        assertThrows(NotFoundException.class, () -> postController.deletePost(4));
    }
}
