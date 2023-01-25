package com.example.BlogWebApp.mappers;


import com.example.BlogWebApp.entities.Comment;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> getCommentsByPostId(@Param("postId") Integer postId);
}
