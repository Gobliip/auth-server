CREATE TABLE oauth_redirect_uris(
  oauth_client_id INT NOT NULL,
  redirect_uri VARCHAR(255) NOT NULL,
  FOREIGN KEY(oauth_client_id) REFERENCES oauth_clients(id)
);