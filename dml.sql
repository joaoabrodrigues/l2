\c orders

INSERT INTO roles (role_name)
VALUES ('ROLE_ADMIN');

INSERT INTO users (email, password)
VALUES ('admin@example.com', '$2a$10$3E38iUKmVf5Smn/dMGG/ierU/C/euJPOqwpj29ktstNEbq6mGo9VW');

INSERT INTO users_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u,
     roles r
WHERE u.email = 'admin@example.com'
  AND r.role_name = 'ROLE_ADMIN';

INSERT INTO boxes (height, width, length)
VALUES
    (30, 40, 80),
    (80, 50, 40),
    (50, 80, 60);
