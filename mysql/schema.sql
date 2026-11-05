-- Base y tablas que necesita el backend (PersonEntity y SessionEntity).
-- MySQL ejecuta este archivo solo la primera vez, cuando el volumen de datos está vacío.
-- Los nombres UK y FK son los que genera Hibernate: así ddl-auto=update no duplica índices.
CREATE DATABASE IF NOT EXISTS persons;
USE persons;

CREATE TABLE IF NOT EXISTS persons (
    id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255),
    name VARCHAR(255),
    password VARCHAR(255),
    phone VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT UK1x5aosta48fbss4d5b3kuu0rd UNIQUE (email)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS sessions (
    id INT NOT NULL AUTO_INCREMENT,
    person_id INT NOT NULL,
    created_at DATETIME(6) NOT NULL,
    token VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT UKnr21vuswfbmr91q57xdbnou5f UNIQUE (token),
    CONSTRAINT FK5s2qa4ybq85kcusnkfa0x573y FOREIGN KEY (person_id) REFERENCES persons (id) ON DELETE CASCADE
) ENGINE=InnoDB;
