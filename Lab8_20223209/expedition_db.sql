DROP DATABASE IF EXISTS expedition_db;

CREATE DATABASE expedition_db;

USE expedition_db;

CREATE TABLE planets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE,
    tipo_planeta VARCHAR(100) NOT NULL,
    habitable BOOLEAN NOT NULL,
    gravedad_relativa DOUBLE NOT NULL,
    descripcion TEXT
);

CREATE TABLE crew_members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(255) NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    rango VARCHAR(100),
    fecha_contratacion DATE NOT NULL
);

CREATE TABLE expeditions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_mision VARCHAR(255) NOT NULL,
    planeta_destino_id BIGINT NOT NULL,
    fecha_lanzamiento DATETIME NOT NULL,
    estado VARCHAR(50) NOT NULL,
    objetivos TEXT,
    resultados TEXT,

    FOREIGN KEY (planeta_destino_id) REFERENCES planets(id)
);

CREATE TABLE expedition_crew (
    expedition_id BIGINT NOT NULL,
    crew_member_id BIGINT NOT NULL,

    PRIMARY KEY (expedition_id, crew_member_id),

    FOREIGN KEY (expedition_id) REFERENCES expeditions(id) ON DELETE CASCADE,
    FOREIGN KEY (crew_member_id) REFERENCES crew_members(id) ON DELETE CASCADE
);