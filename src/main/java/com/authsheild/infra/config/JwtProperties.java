package com.authsheild.infra.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "authshield.jwt")
public record JwtProperties(
        String issuer,
        long accessTokenMinutes,
        long refreshTokenDays,
        String secret
) {}
