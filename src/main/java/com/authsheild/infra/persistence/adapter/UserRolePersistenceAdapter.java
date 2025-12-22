package com.authsheild.infra.persistence.adapter;

import com.authsheild.domain.port.UserRolePort;
import com.authsheild.domain.user.RoleName;
import com.authsheild.infra.persistence.repo.RoleJpaRepository;
import com.authsheild.infra.persistence.repo.UserJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserRolePersistenceAdapter implements UserRolePort {

    private final UserJpaRepository userRepo;
    private final RoleJpaRepository roleRepo;

    public UserRolePersistenceAdapter(UserJpaRepository userRepo, RoleJpaRepository roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Transactional
    @Override
    public void assignRoles(UUID userId, Set<RoleName> roles) {
        var user = userRepo.findById(userId).orElseThrow();
        var roleEntities = roles.stream()
                .map(r -> roleRepo.findByName(r).orElseThrow())
                .collect(Collectors.toSet());
        user.setRoles(roleEntities);
        userRepo.save(user);
    }

    @Override
    public Set<RoleName> getRoles(UUID userId) {
        var user = userRepo.findById(userId).orElseThrow();
        return user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toSet());
    }
}
