INSERT INTO user (id, username, password, name, age) VALUES (1, 'waylau', '$2a$04$UwI4.kbVygm/T3qoe/5lJuqJNOULzyRP7ZH8u0GM7miVgOjZXvdhu', '老卫', 30);
INSERT INTO user (id, username, password, name, age)  VALUES (2, 'admin', '$2a$04$UwI4.kbVygm/T3qoe/5lJuqJNOULzyRP7ZH8u0GM7miVgOjZXvdhu', 'Way Lau', 29);

INSERT INTO authority (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);
