package com.authsheild.domain.port;

import com.authsheild.domain.user.RoleName;

import java.util.Set;
import java.util.UUID;

public interface UserRolePort {
    void assignRoles(UUID userId, Set<RoleName> roles);
    Set<RoleName> getRoles(UUID userId);
}
