package com.authsheild.app.auth;

import com.authsheild.domain.port.JwtPort;
import com.authsheild.app.auth.dto.*;
import com.authsheild.domain.port.RefreshTokenPort;
import com.authsheild.domain.port.UserPort;
import com.authsheild.domain.port.UserRolePort;
import com.authsheild.domain.user.RefreshToken;
import com.authsheild.domain.user.RoleName;
import com.authsheild.domain.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserPort userPort;
    private final UserRolePort userRolePort;
    private final RefreshTokenPort refreshTokenPort;
    private final PasswordEncoder passwordEncoder;
    private final JwtPort jwtPort;
    private final TokenService tokenService;

    private final long refreshDays = 14;

    public AuthService(
            UserPort userPort,
            UserRolePort userRolePort,
            RefreshTokenPort refreshTokenPort,
            PasswordEncoder passwordEncoder,
            JwtPort jwtPort,
            TokenService tokenService
    ) {
        this.userPort = userPort;
        this.userRolePort = userRolePort;
        this.refreshTokenPort = refreshTokenPort;
        this.passwordEncoder = passwordEncoder;
        this.jwtPort = jwtPort;
        this.tokenService = tokenService;
    }

    @Transactional
    public void register(RegisterRequest req) {
        String email = req.email().toLowerCase();

        if (userPort.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        var newUser = new User(
                UUID.randomUUID(),
                email,
                passwordEncoder.encode(req.password()),
                true,
                Set.of(RoleName.ROLE_USER)
        );

        var saved = userPort.save(newUser);
        userRolePort.assignRoles(saved.getId(), Set.of(RoleName.ROLE_USER));
    }

    @Transactional
    public AuthResponse login(LoginRequest req) {
        String email = req.email().toLowerCase();

        var user = userPort.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(req.password(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        var roles = userRolePort.getRoles(user.getId());
        String access = jwtPort.generateAccessToken(
                user.getId().toString(),
                user.getEmail(),
                roles.stream().map(Enum::name).collect(Collectors.toSet())
        );

        String refreshRaw = tokenService.newRefreshToken();
        String refreshHash = tokenService.sha256Base64(refreshRaw);

        refreshTokenPort.save(new RefreshToken(
                UUID.randomUUID(),
                user.getId(),
                refreshHash,
                tokenService.expiresInDays(refreshDays),
                null,
                Instant.now()
        ));

        return new AuthResponse(access, refreshRaw);
    }

    @Transactional
    public AuthResponse refresh(RefreshRequest req) {
        String oldHash = tokenService.sha256Base64(req.refreshToken());

        var old = refreshTokenPort.findByHash(oldHash)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (!old.isActive()) {
            throw new RuntimeException("Refresh token expired/revoked");
        }

        refreshTokenPort.revoke(old.getId(), Instant.now());

        var user = userPort.findById(old.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        var roles = userRolePort.getRoles(user.getId());
        String access = jwtPort.generateAccessToken(
                user.getId().toString(),
                user.getEmail(),
                roles.stream().map(Enum::name).collect(Collectors.toSet())
        );

        String newRaw = tokenService.newRefreshToken();
        String newHash = tokenService.sha256Base64(newRaw);

        refreshTokenPort.save(new RefreshToken(
                UUID.randomUUID(),
                user.getId(),
                newHash,
                tokenService.expiresInDays(refreshDays),
                null,
                Instant.now()
        ));

        return new AuthResponse(access, newRaw);
    }

    @Transactional
    public void logout(RefreshRequest req) {
        String hash = tokenService.sha256Base64(req.refreshToken());
        refreshTokenPort.findByHash(hash).ifPresent(rt -> refreshTokenPort.revoke(rt.getId(), Instant.now()));
    }
}
