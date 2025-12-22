package com.authsheild.domain.user;

import java.time.Instant;
import java.util.UUID;

public class RefreshToken {
    private UUID id;
    private UUID userId;
    private String tokenHash;
    private Instant expiresAt;
    private Instant revokedAt;
    private Instant createdAt;

    public RefreshToken(UUID id, UUID userId, String tokenHash, Instant expiresAt, Instant revokedAt, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
        this.revokedAt = revokedAt;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public String getTokenHash() { return tokenHash; }
    public Instant getExpiresAt() { return expiresAt; }
    public Instant getRevokedAt() { return revokedAt; }
    public Instant getCreatedAt() { return createdAt; }

    public boolean isActive() {
        return revokedAt == null && expiresAt.isAfter(Instant.now());
    }
}
