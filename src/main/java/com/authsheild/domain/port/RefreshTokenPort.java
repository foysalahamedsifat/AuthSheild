package com.authsheild.domain.port;

import com.authsheild.domain.user.RefreshToken;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenPort {
    RefreshToken save(RefreshToken token);
    Optional<RefreshToken> findByHash(String tokenHash);
    void revoke(UUID tokenId, Instant revokedAt);
}
