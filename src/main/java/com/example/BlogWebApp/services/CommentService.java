package com.example.BlogWebApp.services;

import com.example.BlogWebApp.entities.Comment;
import com.example.BlogWebApp.mappers.CommentMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;

    public List<Comment> getCommentsByPostId(Integer postId) {
        return commentMapper.getCommentsByPostId(postId);
    }
}
