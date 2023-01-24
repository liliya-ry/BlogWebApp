package com.example.BlogWebApp.services;

import com.example.BlogWebApp.entities.Post;
import com.example.BlogWebApp.mappers.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    PostMapper postMapper;

    public void insertPost(Post post) {
        postMapper.insertPost(post);
    }

    public Post getPostById(Integer postId) {
        return postMapper.getPostById(postId);
    }

    public List<Post> getAllPosts() {
        return postMapper.getAllPosts();
    }

    public int updatePost(Post post) {
        return postMapper.updatePost(post);
    }

    public int deletePost(Integer postId) {
        return postMapper.deletePost(postId);
    }
}