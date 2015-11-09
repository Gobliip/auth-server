ALTER TABLE auth_users ADD COLUMN created_at DATETIME;
ALTER TABLE auth_users ADD COLUMN updated_at DATETIME;

ALTER TABLE auth_authorities ADD COLUMN created_at DATETIME;
ALTER TABLE auth_authorities ADD COLUMN updated_at DATETIME;

ALTER TABLE oauth_clients ADD COLUMN created_at DATETIME;
ALTER TABLE oauth_clients ADD COLUMN updated_at DATETIME;
