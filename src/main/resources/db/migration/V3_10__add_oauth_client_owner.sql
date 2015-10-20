ALTER TABLE oauth_clients
  ADD COLUMN auth_user_id INT NOT NULL DEFAULT 1,
  ADD FOREIGN KEY(auth_user_id) REFERENCES auth_users(id);

ALTER TABLE oauth_clients ALTER COLUMN auth_user_id DROP DEFAULT;
