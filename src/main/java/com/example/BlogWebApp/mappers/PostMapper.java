package com.example.BlogWebApp.mappers;

import com.example.BlogWebApp.entities.Post;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostMapper {
    Post getPostById(@Param("id") Integer id);
    List<Post> getAllPosts();
    void insertPost(Post post);
    int updatePost(Post post);
    int deletePost(@Param("id") Integer id);
}
