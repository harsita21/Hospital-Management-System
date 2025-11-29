package com.hospital.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

public class PasswordUtil {
    
    // Simple SHA-256 hashing for demonstration
    // In production, use BCrypt or Argon2
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // This is the fix: specify the charset to get consistent bytes
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
        
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        String hashedInput = hashPassword(plainPassword);
        return hashedInput.equals(hashedPassword);
    }
    
    // For the demo admin account (password: admin123)
    // SHA-256 hash of "admin123" encoded in Base64
    public static final String DEMO_ADMIN_HASH = "jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=";
}