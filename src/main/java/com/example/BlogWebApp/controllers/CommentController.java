package com.example.BlogWebApp.controllers;

import com.example.BlogWebApp.entities.Comment;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/comments")
public interface CommentController {
    @GetMapping
    List<Comment> getAllCommentsByPostId(@RequestParam Integer postId);
}
