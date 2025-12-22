package com.authsheild.infra.persistence.adapter;

import com.authsheild.domain.port.RefreshTokenPort;
import com.authsheild.domain.user.RefreshToken;
import com.authsheild.infra.persistence.repo.RefreshTokenJpaRepository;
import com.authsheild.infra.persistence.user.RefreshTokenEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
public class RefreshTokenPersistenceAdapter implements RefreshTokenPort {

    private final RefreshTokenJpaRepository repo;

    public RefreshTokenPersistenceAdapter(RefreshTokenJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public RefreshToken save(RefreshToken token) {
        RefreshTokenEntity e = new RefreshTokenEntity();
        e.setId(token.getId());
        e.setUserId(token.getUserId());
        e.setTokenHash(token.getTokenHash());
        e.setExpiresAt(token.getExpiresAt());
        e.setRevokedAt(token.getRevokedAt());
        e.setCreatedAt(token.getCreatedAt());
        var saved = repo.save(e);
        return toDomain(saved);
    }

    @Override
    public Optional<RefreshToken> findByHash(String tokenHash) {
        return repo.findByTokenHash(tokenHash).map(this::toDomain);
    }

    @Override
    public void revoke(UUID tokenId, Instant revokedAt) {
        repo.findById(tokenId).ifPresent(e -> {
            e.setRevokedAt(revokedAt);
            repo.save(e);
        });
    }

    private RefreshToken toDomain(RefreshTokenEntity e) {
        return new RefreshToken(e.getId(), e.getUserId(), e.getTokenHash(), e.getExpiresAt(), e.getRevokedAt(), e.getCreatedAt());
    }
}
