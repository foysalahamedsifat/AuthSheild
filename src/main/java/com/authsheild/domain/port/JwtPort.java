package com.authsheild.domain.port;

import java.util.Set;

public interface JwtPort {
    String generateAccessToken(String subjectUserId, String email, Set<String> roles);
}
