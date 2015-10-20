CREATE TABLE oauth_grant_types(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  grant_type VARCHAR(255) NOT NULL
);

INSERT INTO oauth_grant_types(grant_type) VALUES ('authorization_code'), ('refresh_token'), ('password');

CREATE TABLE oauth_client_grant_types(
  oauth_client_id INT NOT NULL,
  oauth_grant_type_id INT NOT NULL,
  FOREIGN KEY(oauth_client_id) REFERENCES oauth_clients(id),
  FOREIGN KEY(oauth_grant_type_id) REFERENCES oauth_grant_types(id)
);

INSERT INTO oauth_client_grant_types(oauth_client_id, oauth_grant_type_id)
SELECT oc.id as oauth_client_id, agt.id as oauth_grant_type_id
FROM oauth_clients oc, oauth_grant_types agt
WHERE oc.authorized_grant_types LIKE CONCAT('%', agt.grant_type, '%');

ALTER TABLE oauth_clients DROP COLUMN authorized_grant_types;
