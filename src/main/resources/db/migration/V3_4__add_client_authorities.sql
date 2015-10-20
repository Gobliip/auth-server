ALTER TABLE oauth_clients DROP COLUMN authorities;

CREATE TABLE oauth_client_authorities(
  oauth_client_id INT NOT NULL,
  authority_id INT NOT NULL,
  FOREIGN KEY(oauth_client_id) REFERENCES oauth_clients(id),
  FOREIGN KEY(authority_id) REFERENCES authorities(id)
);
