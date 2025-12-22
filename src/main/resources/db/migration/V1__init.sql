CREATE TABLE users (
                       id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
                       email NVARCHAR(320) NOT NULL UNIQUE,
                       password_hash NVARCHAR(200) NOT NULL,
                       is_active BIT NOT NULL DEFAULT 1,
                       created_at DATETIME2 NOT NULL,
                       updated_at DATETIME2 NOT NULL
);

CREATE TABLE roles (
                       id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
                       name NVARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE user_roles (
                            user_id UNIQUEIDENTIFIER NOT NULL,
                            role_id UNIQUEIDENTIFIER NOT NULL,
                            CONSTRAINT pk_user_roles PRIMARY KEY (user_id, role_id),
                            CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id),
                            CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE refresh_tokens (
                                id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
                                user_id UNIQUEIDENTIFIER NOT NULL,
                                token_hash NVARCHAR(200) NOT NULL,
                                family_id UNIQUEIDENTIFIER NOT NULL,
                                expires_at DATETIME2 NOT NULL,
                                revoked_at DATETIME2 NULL,
                                replaced_by_token_id UNIQUEIDENTIFIER NULL,
                                created_at DATETIME2 NOT NULL,
                                CONSTRAINT fk_refresh_tokens_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX ix_refresh_tokens_user ON refresh_tokens(user_id);
CREATE INDEX ix_refresh_tokens_family ON refresh_tokens(family_id);

INSERT INTO roles (id, name) VALUES (NEWID(), 'USER');
INSERT INTO roles (id, name) VALUES (NEWID(), 'ADMIN');
