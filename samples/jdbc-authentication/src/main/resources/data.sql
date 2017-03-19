insert into users (username, password, enabled) values ('waylau', '123456', true);
insert into users (username, password, enabled) values ('admin', '123456', true);

insert into authorities (username, authority) values ('waylau', 'ROLE_USER');
insert into authorities (username, authority) values ('admin', 'ROLE_USER');
insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN');