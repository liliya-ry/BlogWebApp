package com.example.BlogWebApp.auth;

import com.example.BlogWebApp.entities.User;
import com.example.BlogWebApp.mappers.UserMapper;
import com.example.BlogWebApp.utility.PasswordEncryptor;

import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Base64;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String[] authPair = getAuthPair(request);
        if (authPair == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        String username = authPair[0];
        String password = authPair[1];
        User user = userMapper.getUser(username);

        boolean isAuthenticated = authenticate(user, password);
        if (!isAuthenticated) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        request.setAttribute("user", username);

        boolean isAuthorized = authorize(user, handler);
        if (!isAuthorized) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        return true;
    }

    private boolean authorize(User user, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Role roleAnn = handlerMethod.getMethodAnnotation(Role.class);
        if (roleAnn == null)
            return true;

        String role = roleAnn.value();
        return role.equals(user.role);
    }

    private boolean authenticate(User user, String password) {
        if (user == null)
            return false;

        String encryptedPassword = PasswordEncryptor.encryptPassword(password + user.salt);
        return encryptedPassword.equals(user.password);
    }

    private String[] getAuthPair(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;

        String encodedStr = authHeader.split(" ")[1];
        byte[] decodedBytes = Base64.getDecoder().decode(encodedStr);
        String authStr = new String(decodedBytes);
        return authStr.split(":");
    }
}
