ALTER TABLE dbo.refresh_tokens
ALTER COLUMN created_at datetimeoffset(7) NOT NULL;

ALTER TABLE dbo.refresh_tokens
ALTER COLUMN expires_at datetimeoffset(7) NOT NULL;

ALTER TABLE dbo.refresh_tokens
ALTER COLUMN revoked_at datetimeoffset(7) NULL;
