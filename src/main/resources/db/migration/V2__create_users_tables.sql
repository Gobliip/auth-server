create table users(
	username VARCHAR(255) not null primary key,
	password VARCHAR(500) not null,
	enabled boolean not null
);
create table authorities (
	username VARCHAR(255) not null,
	authority VARCHAR(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);