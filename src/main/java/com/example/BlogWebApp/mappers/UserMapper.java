package com.example.BlogWebApp.mappers;

import com.example.BlogWebApp.entities.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User getUser(String username);
    void insertUser(User user);
}
