package com.example.BlogWebApp;

import com.example.BlogWebApp.auth.AuthInterceptor;
import com.example.BlogWebApp.logging.LoggingInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
@Configuration
public class Config implements WebMvcConfigurer {
    @Autowired
    private LoggingInterceptor loggingInterceptor;
    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor);
        registry.addInterceptor(loggingInterceptor);
    }
}
