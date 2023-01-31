package com.example.BlogWebApp.auth;

import com.example.BlogWebApp.entities.User;
import com.example.BlogWebApp.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userMapper.getUser(username);
        if (user == null)
            throw new BadCredentialsException("Invalid username");

        String encodedPassword = PasswordEncryptor.encryptPassword(password + user.salt);
        if (!encodedPassword.equals(user.password))
            throw new BadCredentialsException("Invalid password");

        return new UsernamePasswordAuthenticationToken(username, password, getGrantedAuthorities());
    }

    private List<GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_READ"));
        authorityList.add(new SimpleGrantedAuthority("ROLE_WRITE"));
        return authorityList;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
