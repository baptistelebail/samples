DROP TABLE IF EXISTS account;

CREATE TABLE account
(
    id            VARCHAR(36)  NOT NULL PRIMARY KEY,
    username      VARCHAR(255) NOT NULL UNIQUE,
    active        BOOLEAN      NOT NULL DEFAULT FALSE
);