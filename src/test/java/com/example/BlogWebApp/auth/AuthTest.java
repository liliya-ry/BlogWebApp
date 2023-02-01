package com.example.BlogWebApp.auth;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.BlogWebApp.entities.*;
import com.example.BlogWebApp.mappers.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    UserMapper userMapper;
    @MockBean
    PostMapper postMapper;

    @BeforeEach
    void setUp() {
        User admin = new User("lili", "1234", "lili@abv.bg", "liliya", "yordanova", "admin");
        admin.password = PasswordEncryptor.encryptPassword(admin.password + admin.generateSalt());
        when(userMapper.getUser(admin.username)).thenReturn(admin);

        User user = new User("maria", "5678", "maria@abv.bg", "maria", "petrova", "user");
        user.password = PasswordEncryptor.encryptPassword(user.password + user.generateSalt());
        when(userMapper.getUser(user.username)).thenReturn(user);

        when(postMapper.getAllPosts()).thenReturn(null);
    }

    @Test
    @DisplayName("Authentication - Missing Authorization header")
    void authentication1() throws Exception {
        mockMvc
                .perform(get("/posts"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Admin authentication - no role method")
    void authentication2() throws Exception {
        String credentials = "lili:1234";
        byte[] encodedBytes = Base64.getEncoder().encode(credentials.getBytes());
        String encodedCredentials = new String(encodedBytes);
        mockMvc
                .perform(get("/posts")
                        .header("Authorization", "Basic " + encodedCredentials))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("User authentication - no role method")
    void authentication3() throws Exception {
        String credentials = "maria:5678";
        byte[] encodedBytes = Base64.getEncoder().encode(credentials.getBytes());
        String encodedCredentials = new String(encodedBytes);
        mockMvc
                .perform(get("/posts")
                        .header("Authorization", "Basic " + encodedCredentials))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Register user - no authentication")
    void authentication4() throws Exception {
        String userJson = """
            {
                "username": "petya",
                "password": "1234",
                "email": "p@abv.bg",
                "firstName": "petya",
                "lastName": "petrova",
                "role": "user"
            }
                """;
        mockMvc
                .perform(post("/users/register")
                        .content(userJson)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Admin role - Missing Authorization header")
    void authorization1() throws Exception {
        mockMvc
                .perform(get("/users"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Admin authorization - admin role method")
    void authorization2() throws Exception {
        String credentials = "lili:1234";
        byte[] encodedBytes = Base64.getEncoder().encode(credentials.getBytes());
        String encodedCredentials = new String(encodedBytes);
        mockMvc
                .perform(get("/users")
                        .header("Authorization", "Basic " + encodedCredentials))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("User authorization - admin role method")
    void authorization3() throws Exception {
        String credentials = "maria:5678";
        byte[] encodedBytes = Base64.getEncoder().encode(credentials.getBytes());
        String encodedCredentials = new String(encodedBytes);
        mockMvc
                .perform(get("/users")
                        .header("Authorization", "Basic " + encodedCredentials))
                .andExpect(status().isForbidden());
    }
}