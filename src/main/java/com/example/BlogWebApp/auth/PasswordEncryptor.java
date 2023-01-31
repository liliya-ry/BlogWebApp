package com.example.BlogWebApp.auth;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptor implements PasswordEncoder {
    @Override
    public String encode(CharSequence password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(password.toString().getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String res = no.toString(16);

            while (res.length() < 32) {
                res = "0" + res;
            }

            return res;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(encode(rawPassword));
    }
}
