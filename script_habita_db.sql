CREATE TABLE tipos_habitacion (
    id_tipo SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

INSERT INTO tipos_habitacion (nombre) VALUES ('Sencilla'), ('Doble'), ('Premium');

CREATE TABLE habitaciones (
    numero INT PRIMARY KEY,
    id_tipo INT REFERENCES tipos_habitacion(id_tipo),
    precio NUMERIC(10,2),
    estado VARCHAR(20) DEFAULT 'disponible'
);
