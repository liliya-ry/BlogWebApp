package com.example.BlogWebApp.mappers;

import com.example.BlogWebApp.entities.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    User getUser(String username);
    List<User> getAllUsers();
    void insertUser(User user);
    int updateUser(User user);
    int deleteUser(@Param("username") String username);
}
