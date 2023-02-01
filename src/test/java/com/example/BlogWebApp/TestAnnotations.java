package com.example.BlogWebApp;

import com.example.BlogWebApp.controllers.PostController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestAnnotations {
    @Autowired
    PostController postController;
	@Test
    void test1() {

    }
}
