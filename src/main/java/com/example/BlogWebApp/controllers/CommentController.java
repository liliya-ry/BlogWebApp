package com.example.BlogWebApp.controllers;

import com.example.BlogWebApp.entities.Comment;
import com.example.BlogWebApp.mappers.CommentMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/blogApp/comments"})
public class CommentController {
    @Autowired
    CommentMapper commentMapper;

    @GetMapping
    public List<Comment> getAllCommentsByPostId(@RequestParam Integer postId) {
        return commentMapper.getCommentsByPostId(postId);
    }
}
