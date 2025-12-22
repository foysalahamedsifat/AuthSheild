package com.authsheild.domain.port;

import com.authsheild.domain.user.RoleName;

public interface RolePort {
    boolean roleExists(RoleName role);
}
