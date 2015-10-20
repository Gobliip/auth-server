CREATE TABLE oauth_scopes(
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO oauth_scopes(name) VALUES ('openid');

CREATE TABLE oauth_client_scopes(
  oauth_client_id INT NOT NULL,
  oauth_scope_id INT NOT NULL,
  FOREIGN KEY(oauth_client_id) REFERENCES oauth_clients(id),
  FOREIGN KEY(oauth_scope_id) REFERENCES oauth_scopes(id)
);

INSERT INTO oauth_client_scopes(oauth_client_id, oauth_scope_id)
  SELECT oc.id as oauth_client_id, os.id as oauth_scope_id
  FROM oauth_clients oc, oauth_scopes os
  WHERE os.name = 'openid';

CREATE TABLE oauth_client_auto_approve_scopes(
  oauth_client_id INT NOT NULL,
  oauth_scope_id INT NOT NULL,
  FOREIGN KEY(oauth_client_id) REFERENCES oauth_clients(id),
  FOREIGN KEY(oauth_scope_id) REFERENCES oauth_scopes(id)
);

ALTER TABLE oauth_clients DROP COLUMN scope;
