package com.authsheild.domain.port;

import com.authsheild.domain.user.User;

import java.util.Optional;
import java.util.UUID;

public interface UserPort {
    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id);
    boolean existsByEmail(String email);
    User save(User user);
}
