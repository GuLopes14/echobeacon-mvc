CREATE TABLE echobeacon_user (
        id BIGSERIAL PRIMARY KEY,
        name VARCHAR(255),
        email VARCHAR(255) UNIQUE NOT NULL,
        picture VARCHAR(512)
);
