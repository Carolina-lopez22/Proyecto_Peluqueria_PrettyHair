CREATE DATABASE IF NOT EXISTS peluqueria;
USE peluqueria;

CREATE TABLE IF NOT EXISTS clientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(8)NOT NULL
);
CREATE TABLE IF NOT EXISTS  servicios (
    id_servicio INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS  citas (
    id_cita INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_servicio INT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    duracion INT NOT NULL,
    estado VARCHAR(20) NOT NULL
    CHECK (estado IN ('pendiente','confirmada','cancelada')),

    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente),
    FOREIGN KEY (id_servicio) REFERENCES servicios(id_servicio)
);
INSERT IGNORE INTO clientes (nombre, telefono)
 VALUES
('María López', '45871234'),
('Carlos Hernández', '51236789'),
('Ana Martínez', '38964521');

INSERT IGNORE INTO servicios (nombre, precio) VALUES
('Corte de cabello', 50.00),
('Lavado de cabello', 30.00),
('Peinado', 60.00),
('Tinte', 150.00),
('Mechas', 200.00),
('Planchado', 250.00),
('Keratina', 300.00),
('Tratamiento capilar', 150.00),
('Ondulación Permanente', 300),
('Alisado Permanente',300);

INSERT IGNORE INTO citas (id_cliente, id_servicio, fecha_hora, duracion, estado) VALUES
(1, 1, '2026-09-30 10:00:00', 60, 'pendiente'),
(2, 4, '2026-09-29 14:30:00', 80,  'confirmada');
