package com.example.BlogWebApp.controllers;

import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import com.example.BlogWebApp.auth.PasswordEncryptor;
import com.example.BlogWebApp.entities.*;
import com.example.BlogWebApp.mappers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import java.util.*;

@SpringBootTest
@Import(PostControllerTestConfig.class)
@AutoConfigureMockMvc
class PostControllerTest {
    private String encodedCredentials;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        String credentials = "maria:5678";
        byte[] encodedBytes = Base64.getEncoder().encode(credentials.getBytes());
        encodedCredentials = new String(encodedBytes);

        User user = new User("maria", "5678", "maria@abv.bg", "maria", "petrova", "user");
        user.password = PasswordEncryptor.encryptPassword(user.password + user.generateSalt());
        when(userMapper.getUser(user.username)).thenReturn(user);
    }

    @Test
    void getAllPosts() throws Exception {
        Post post1 = new Post(1, "some title 1", "some body 1");
        Post post2 = new Post(2, "some title 2", "some body 2");
        List<Post> expectedResultList = List.of(post1, post2);
        String expectedResultJson = objectMapper.writeValueAsString(expectedResultList);
        mockMvc.perform(get("/posts")
                .header("Authorization", "Basic " + encodedCredentials))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(expectedResultJson));
    }

    @Test
    void getExistingPost() throws Exception {
        Post post = new Post(3, 2, "some title 2", "some body 2");
        String expectedResultJson = objectMapper.writeValueAsString(post);
        mockMvc.perform(get("/posts/3")
                        .header("Authorization", "Basic " + encodedCredentials))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(expectedResultJson));
    }

    @Test
    void getNonExistingPost() throws Exception {
        ErrorResponse errorResponse = new ErrorResponse(404, "No post with id 1");
        String expectedResultJson = objectMapper.writeValueAsString(errorResponse);
        mockMvc.perform(get("/posts/1")
                        .header("Authorization", "Basic " + encodedCredentials))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedResultJson));
    }

    @Test
    void responseContainsErrorCode() throws Exception {
        mockMvc.perform(get("/posts/1")
                        .header("Authorization", "Basic " + encodedCredentials))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(String.valueOf(SC_NOT_FOUND))));
    }

    @Test
    void responseContainsId() throws Exception {
        String id = "1";
        mockMvc.perform(get("/posts/" + id)
                        .header("Authorization", "Basic " + encodedCredentials))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(id)));
    }

    @Test
    void addPost() throws Exception {
        Post post = new Post(1, "some title 1", "some body 1");
        String requestJson = objectMapper.writeValueAsString(post);
        mockMvc.perform(post("/posts")
                        .header("Authorization", "Basic " + encodedCredentials)
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(requestJson));
    }
}
