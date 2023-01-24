package com.example.BlogWebApp.mappers;


import com.example.BlogWebApp.entities.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {
    List<Comment> getCommentsByPostId(@Param("postId") Integer postId);
}
