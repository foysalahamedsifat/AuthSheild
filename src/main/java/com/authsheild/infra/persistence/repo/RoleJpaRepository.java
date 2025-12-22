package com.authsheild.infra.persistence.repo;

import com.authsheild.domain.user.RoleName;
import com.authsheild.infra.persistence.user.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, UUID> {
    Optional<RoleEntity> findByName(RoleName name);
}
