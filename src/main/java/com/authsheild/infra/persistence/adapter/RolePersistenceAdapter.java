package com.authsheild.infra.persistence.adapter;

import com.authsheild.domain.port.RolePort;
import com.authsheild.domain.user.RoleName;
import com.authsheild.infra.persistence.repo.RoleJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class RolePersistenceAdapter implements RolePort {

    private final RoleJpaRepository repo;

    public RolePersistenceAdapter(RoleJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public boolean roleExists(RoleName role) {
        return repo.findByName(role).isPresent();
    }
}
