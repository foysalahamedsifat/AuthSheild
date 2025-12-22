package com.authsheild.app.auth;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Service
public class TokenService {

    public String newRefreshToken() {
        return UUID.randomUUID() + "." + UUID.randomUUID();
    }

    public String sha256Base64(String raw) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(raw.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Hashing failed", e);
        }
    }

    public Instant expiresInDays(long days) {
        return Instant.now().plusSeconds(days * 24 * 60 * 60);
    }
}
