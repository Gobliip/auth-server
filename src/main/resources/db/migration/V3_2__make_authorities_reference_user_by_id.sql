ALTER TABLE authorities ADD id INT AUTO_INCREMENT PRIMARY KEY;

CREATE TABLE user_authorities(
 user_id INT NOT NULL,
 authority_id INT NOT NULL,
 FOREIGN KEY(user_id) REFERENCES users(id),
 FOREIGN KEY(authority_id) REFERENCES authorities(id),
 PRIMARY KEY(user_id, authority_id)
);

INSERT INTO user_authorities(user_id, authority_id)
  SELECT u.id AS user_id, a.id AS authority_id FROM users u, authorities a
    WHERE u.username = a.username;

ALTER TABLE authorities DROP COLUMN username;
