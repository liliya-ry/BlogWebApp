package com.example.BlogWebApp.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.BlogWebApp.entities.*;
import com.example.BlogWebApp.exceptions.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@Import(PostControllerTestConfig.class)
class PostControllerTest {
    @Autowired
    private PostController postController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
        this.mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    void getAllPosts() throws Exception {
        Post post1 = new Post(4, 1, "st", "sb");
        Post post2 = new Post(3, 2, "some title 2", "some body 2");
        String expectedJson1 = objectMapper.writeValueAsString(post1);
        String expectedJson2 = objectMapper.writeValueAsString(post2);
        mockMvc
                .perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString(), containsString(expectedJson1)))
                .andExpect(result -> assertThat(result.getResponse().getContentAsString(), containsString(expectedJson2)));
    }

    @Test
    void getExistingPost() throws Exception {
        Post expectedPost = new Post(2, 1, "some title 1", "some body 1");
        String expectedJson = objectMapper.writeValueAsString(expectedPost);
        mockMvc
                .perform(get("/posts/2"))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString(), is(expectedJson)));
    }

    @Test
    void getNonExistingPost() {
        assertThrows(NotFoundException.class, () -> postController.getPostById(1));
    }

    @Test
    void addPost() throws Exception {
        Post post = new Post(4, 1, "st", "sb");
        String postJson = objectMapper.writeValueAsString(post);
        mockMvc
                .perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postJson))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString(), is(postJson)));
    }

    @Test
    void updateExistingPost() throws Exception {
        Post post = new Post(3, 1, "new title", "sb");
        String postJson = objectMapper.writeValueAsString(post);
        mockMvc
                .perform(put("/posts/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postJson))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString(), is(postJson)));
    }

    @Test
    void updateNonExistingPost() {
        Post post = new Post(1, 1, "new title", "sb");
        assertThrows(NotFoundException.class, () -> postController.updatePost(post, 1));
    }

    @Test
    void deleteExistingPost() throws Exception {
        mockMvc
                .perform(delete("/posts/2"))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString(), is("2")));
    }

    @Test
    void deleteNonExistingPost() {
        assertThrows(NotFoundException.class, () -> postController.deletePost(5));
    }
}
