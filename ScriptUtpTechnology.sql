-- Crear la base de datos y usarla
CREATE DATABASE IF NOT EXISTS utp_bd;
USE utp_bd;

-- Crear tabla rol
CREATE TABLE rol (
    id_rol INT PRIMARY KEY AUTO_INCREMENT,
    nombre_rol VARCHAR(50) NOT NULL,
    descripcion VARCHAR(100)
);

-- Crear tabla productos
CREATE TABLE productos (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(50) NOT NULL,
  imagen VARCHAR(200) NOT NULL,
  precio DECIMAL(10,2) NOT NULL,
  stock INT NOT NULL
);

-- Crear tabla usuarios (modificada para usar id_rol)
CREATE TABLE usuarios (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(50) NOT NULL,
  correo VARCHAR(100) UNIQUE NOT NULL,
  clave VARCHAR(255) NOT NULL,
  id_rol INT NOT NULL,
  FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
);

-- Crear tabla clientes
CREATE TABLE clientes (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(50) NOT NULL,
  apellido VARCHAR(50) NOT NULL,
  dni VARCHAR(8) UNIQUE NOT NULL,
  telefono VARCHAR(9) NOT NULL,
  correo VARCHAR(100) UNIQUE NOT NULL,
  usuario_id INT NOT NULL,
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- ALTER TABLE clientes add column usuario_id int;
-- ALTER TABLE clientes ADD FOREIGN KEY (usuario_id) REFERENCES usuarios(id);

-- Crear tabla pedidos
CREATE TABLE pedidos (
  id INT PRIMARY KEY AUTO_INCREMENT,
  cliente_id INT NOT NULL,
  usuario_id INT NOT NULL,
  fecha DATE NOT NULL,
  estado VARCHAR(20) NOT NULL DEFAULT 'Pendiente',
  FOREIGN KEY (cliente_id) REFERENCES clientes(id),
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Crear tabla detalles de pedido
CREATE TABLE detalles_pedido (
  id INT PRIMARY KEY AUTO_INCREMENT,
  pedido_id INT NOT NULL,
  producto_id INT NOT NULL,
  cantidad INT NOT NULL,
  precio_unitario DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
  FOREIGN KEY (producto_id) REFERENCES productos(id)
);



-- Crear tabla comprobantes
CREATE TABLE comprobantes (
  id INT PRIMARY KEY AUTO_INCREMENT,
  pedido_id INT NOT NULL,
  tipo_comprobante VARCHAR(20) NOT NULL,
  fecha_emision DATE NOT NULL,
  total DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (pedido_id) REFERENCES pedidos(id)
);

-- Insertar roles
INSERT INTO rol (nombre_rol, descripcion) VALUES
('Administrador', 'Acceso total al sistema'),
('Vendedor', 'Gestión de ventas'),
('Cliente', 'Clientes, acceso a pedidos, carrito, etc');

-- Insertar 10 productos
INSERT INTO productos (nombre, imagen, precio, stock) VALUES
('Audífonos Bluetooth', 'https://images.pexels.com/photos/16688488/pexels-photo-16688488.jpeg', 35.00, 10),
('Disco Duro 1TB', 'https://images.pexels.com/photos/20076003/pexels-photo-20076003.jpeg', 220.00, 8),
('Cámara Deportiva', 'https://images.pexels.com/photos/11002709/pexels-photo-11002709.jpeg', 150.00, 5),
('Auriculares Gaming', 'https://images.pexels.com/photos/3921803/pexels-photo-3921803.jpeg', 60.00, 12),
('Smartwatch', 'https://images.pexels.com/photos/1034653/pexels-photo-1034653.jpeg', 180.00, 9),
('Tarjeta de Video GTX 1660', 'https://images.pexels.com/photos/4581903/pexels-photo-4581903.jpeg', 350.00, 4),
('Celular Android', 'https://images.pexels.com/photos/10343717/pexels-photo-10343717.jpeg', 780.00, 6),
('Laptop i5', 'https://images.pexels.com/photos/577210/pexels-photo-577210.jpeg', 3200.00, 7),
('Teclado Mecánico', 'https://images.pexels.com/photos/267389/pexels-photo-267389.jpeg', 90.00, 10),
('Mouse Inalámbrico', 'https://images.pexels.com/photos/392018/pexels-photo-392018.jpeg', 45.00, 15);

-- Insertar 5 usuarios (ahora con id_rol)
INSERT INTO usuarios (nombre, correo, clave, id_rol) VALUES
('Administrador General', 'admin@tienda.com', 'admin123', 1), -- Administrador
('Vendedor 1', 'vendedor1@tienda.com', 'clave1', 2), -- Vendedor
('Vendedor 2', 'vendedor2@tienda.com', 'clave2', 2), -- Vendedor
('Cliente', 'soporte@tienda.com', 'soporte123', 3), -- Soporte
('Cliente2', 'almacen@tienda.com', 'almacen123', 3); -- Almacén

-- Insertar 5 clientes
INSERT INTO clientes (nombre, apellido, dni, telefono, correo, usuario_id) VALUES
('Juan', 'Pérez', '78945612', '987654321', 'juan.perez@example.com', 4),
('María', 'Rodríguez', '12345678', '999888777', 'maria.rodriguez@example.com', 5);

-- Insertar 5 pedidos
INSERT INTO pedidos (cliente_id, usuario_id, fecha, estado) VALUES
(1, 1, '2024-05-01', 'Pendiente'),
(2, 2, '2024-05-02', 'En proceso'),
(1, 3, '2024-05-03', 'Completado'),
(2, 1, '2024-05-04', 'Pendiente'),
(1, 2, '2024-05-05', 'Cancelado');

-- Insertar 5 detalles de pedido
INSERT INTO detalles_pedido (pedido_id, producto_id, cantidad, precio_unitario) VALUES
(1, 1, 2, 35.00),
(1, 2, 1, 220.00),
(2, 3, 1, 150.00),
(3, 4, 1, 60.00),
(4, 5, 2, 180.00);


-- Insertar 5 comprobantes
INSERT INTO comprobantes (pedido_id, tipo_comprobante, fecha_emision, total) VALUES
(11, 'Boleta', '2024-05-01', 290.00),
(12, 'Factura', '2024-05-02', 150.00),
(13, 'Boleta', '2024-05-03', 60.00),
(14, 'Factura', '2024-05-04', 360.00),
(15, 'Boleta', '2024-05-05', 0.00);


