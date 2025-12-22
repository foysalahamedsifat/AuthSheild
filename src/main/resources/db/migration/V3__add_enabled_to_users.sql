ALTER TABLE dbo.users
    ADD enabled bit NOT NULL
    CONSTRAINT DF_users_enabled DEFAULT (1);
