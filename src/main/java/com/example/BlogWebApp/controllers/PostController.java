package com.example.BlogWebApp.controllers;

import static com.example.BlogWebApp.entities.ErrorResponse.NO_POST_MESSAGE;

import com.example.BlogWebApp.entities.*;
import com.example.BlogWebApp.exceptions.PostNotFoundException;
import com.example.BlogWebApp.mappers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/blogApp/posts")
public class PostController {
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public List<Post> getAllPosts() {
        return postMapper.getAllPosts();
    }

    @GetMapping("/{id}")
    public Object getPostById(@PathVariable Integer id) throws JsonProcessingException {
        Post post = postMapper.getPostById(id);
        if (post == null)
            throwPostException(id, "");

        return post;
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getCommentsByPostId(@PathVariable Integer id) {
        return commentMapper.getCommentsByPostId(id);
    }

    @PostMapping
    public Object createPost(@RequestBody Post post) {
        postMapper.insertPost(post);
        return post;
    }

    @PutMapping("/{id}")
    public Object updatePost(@RequestBody Post post, @PathVariable Integer id) throws JsonProcessingException {
        post.id = id;

        int affectedRows = postMapper.updatePost(post);
        if (affectedRows != 1)
            throwPostException(id, " was updated");

        return post;
    }

    @DeleteMapping("/{id}")
    public Object deletePost(@PathVariable Integer id) throws JsonProcessingException {
        int affectedRows = postMapper.deletePost(id);
        if (affectedRows != 1)
            throwPostException(id, " was deleted");

        return id;
    }

    private void throwPostException(Integer id, String extraText) throws JsonProcessingException {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), NO_POST_MESSAGE + id + extraText);
        String jsonError = objectMapper.writeValueAsString(errorResponse);
        throw new PostNotFoundException(jsonError);
    }
}
