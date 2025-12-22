package com.authsheild.domain.user;

import java.util.Set;
import java.util.UUID;

public class User {
    private UUID id;
    private String email;
    private String passwordHash;
    private boolean enabled;
    private Set<RoleName> roles;

    public User(UUID id, String email, String passwordHash, boolean enabled, Set<RoleName> roles) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.enabled = enabled;
        this.roles = roles;
    }

    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public boolean isEnabled() { return enabled; }
    public Set<RoleName> getRoles() { return roles; }
}
