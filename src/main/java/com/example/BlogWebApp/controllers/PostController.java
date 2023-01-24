package com.example.BlogWebApp.controllers;

import static com.example.BlogWebApp.entities.ErrorResponse.NO_POST_MESSAGE;

import com.example.BlogWebApp.entities.*;
import com.example.BlogWebApp.exceptions.PostNotFoundException;
import com.example.BlogWebApp.services.PostService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/blogApp/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommentController commentController;

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Object getPostById(@PathVariable Integer id) throws JsonProcessingException {
        Post post = postService.getPostById(id);
        if (post == null)
            throwPostException(id, "");

        return post;
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getCommentsByPostId(@PathVariable Integer id, Model model) {
        model.addAttribute("postId", id);
        return commentController.getAllCommentsByPostId(null, model);
    }

    @PostMapping
    public Object createPost(@RequestBody String body) throws JsonProcessingException {
        Post post = objectMapper.readValue(body, Post.class);
        postService.insertPost(post);
        return post;
    }

    @PutMapping("/{id}")
    public Object updatePost(@RequestBody String body, @PathVariable Integer id) throws JsonProcessingException {
        Post post = objectMapper.readValue(body, Post.class);
        post.setId(id);

        int affectedRows = postService.updatePost(post);
        if (affectedRows != 1)
            throwPostException(id, " was updated");

        return post;
    }

    @DeleteMapping("/{id}")
    public Object deletePost(@PathVariable Integer id) throws JsonProcessingException {
        int affectedRows = postService.deletePost(id);
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
