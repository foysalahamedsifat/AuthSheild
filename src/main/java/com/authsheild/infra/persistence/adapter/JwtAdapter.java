package com.authsheild.infra.persistence.adapter;

import com.authsheild.domain.port.JwtPort;
import com.authsheild.infra.config.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Component
public class JwtAdapter implements JwtPort {

    private final JwtProperties props;
    private final SecretKey key;

    public JwtAdapter(JwtProperties props) {
        this.props = props;
        this.key = Keys.hmacShaKeyFor(props.secret().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generateAccessToken(String subjectUserId, String email, Set<String> roles) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(props.accessTokenMinutes() * 60);

        return Jwts.builder()
                .issuer(props.issuer())
                .subject(subjectUserId)
                .claim("email", email)
                .claim("roles", roles)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key)
                .compact();
    }
}
