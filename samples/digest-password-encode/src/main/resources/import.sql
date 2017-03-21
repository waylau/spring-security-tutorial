INSERT INTO user (id, username, password, name, age) VALUES (1, 'waylau', 'b7ace5658b44f7295e7e8e36da421502', '老卫', 30);
INSERT INTO user (id, username, password, name, age)  VALUES (2, 'admin', 'b7b20c789238e2a46e56b533c87e673c', 'Way Lau', 29);

INSERT INTO authority (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);
