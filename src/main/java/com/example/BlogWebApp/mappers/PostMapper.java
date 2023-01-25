package com.example.BlogWebApp.mappers;

import com.example.BlogWebApp.entities.Post;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface PostMapper {
    Post getPostById(@Param("id") Integer id);
    List<Post> getAllPosts();
    void insertPost(Post post);
    int updatePost(Post post);
    int deletePost(@Param("id") Integer id);
}
