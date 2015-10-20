CREATE TABLE oauth_resources(
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE oauth_client_resources(
  oauth_client_id INT NOT NULL,
  oauth_resource_id INT NOT NULL,
  FOREIGN KEY(oauth_client_id) REFERENCES oauth_clients(id),
  FOREIGN KEY(oauth_resource_id) REFERENCES oauth_resources(id)
);
