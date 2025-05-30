CREATE DATABASE orders;

\c orders

CREATE TABLE roles
(
    id        BIGSERIAL PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL
);


CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE users_roles
(
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);


CREATE TABLE boxes
(
   id BIGSERIAL PRIMARY KEY,
   height INTEGER NOT NULL,
   width INTEGER NOT NULL,
   length INTEGER NOT NULL
);
