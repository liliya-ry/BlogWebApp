package com.example.BlogWebApp.controllers;

import com.example.BlogWebApp.entities.Comment;
import com.example.BlogWebApp.services.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/blogApp/comments"})
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping
    public List<Comment> getAllCommentsByPostId(@RequestParam Integer postId, Model model) {
        if (postId == null) {
            postId = (Integer) model.getAttribute("postId");
        }
        return commentService.getCommentsByPostId(postId);
    }
}
