package com.example.BlogWebApp.controllers;

import com.example.BlogWebApp.entities.Comment;
import com.example.BlogWebApp.mappers.CommentMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentControllerImpl implements CommentController {
    @Autowired
    CommentMapper commentMapper;

    public List<Comment> getAllCommentsByPostId(Integer postId) {
        return commentMapper.getCommentsByPostId(postId);
    }
}
