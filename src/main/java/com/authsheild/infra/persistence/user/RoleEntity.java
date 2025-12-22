package com.authsheild.infra.persistence.user;

import com.authsheild.domain.user.RoleName;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 50)
    private RoleName name;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public RoleName getName() { return name; }
    public void setName(RoleName name) { this.name = name; }
}
