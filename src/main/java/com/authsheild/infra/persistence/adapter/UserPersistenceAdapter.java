package com.authsheild.infra.persistence.adapter;

import com.authsheild.domain.port.UserPort;
import com.authsheild.domain.user.RoleName;
import com.authsheild.domain.user.User;
import com.authsheild.infra.persistence.repo.UserJpaRepository;
import com.authsheild.infra.persistence.user.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserPersistenceAdapter implements UserPort {

    private final UserJpaRepository repo;

    public UserPersistenceAdapter(UserJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repo.findByEmail(email).map(this::toDomain);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return repo.findById(id).map(this::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repo.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        UserEntity e = new UserEntity();
        e.setId(user.getId());
        e.setEmail(user.getEmail());
        e.setPasswordHash(user.getPasswordHash());
        e.setEnabled(user.isEnabled());
        // roles set later in AuthService (via RoleJpaRepository) OR you can map properly
        UserEntity saved = repo.save(e);
        return toDomain(saved);
    }

    private User toDomain(UserEntity e) {
        Set<RoleName> roles = e.getRoles().stream().map(r -> r.getName()).collect(Collectors.toSet());
        return new User(e.getId(), e.getEmail(), e.getPasswordHash(), e.isEnabled(), roles);
    }
}
