package com.shopme.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordEncoderTest {
    @Test
    public void testEncoderPassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "igor1234";
        String encodedpassword = passwordEncoder.encode(rawPassword);
        System.out.println(encodedpassword);
        boolean matches = passwordEncoder.matches(rawPassword,encodedpassword);
        assertTrue(matches);
    }
}
