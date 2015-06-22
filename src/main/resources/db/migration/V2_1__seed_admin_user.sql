INSERT INTO users (username, password, enabled) VALUES ('admin','$2a$10$KDjSjfZO.jhvzN01SGAR5OKUdksgZUFaC5LWI1XOVYS0NH8kbWQHW',1);
INSERT INTO authorities (username, authority) VALUES ('admin','ROLE_ADMIN'),('admin','ROLE_USER');
