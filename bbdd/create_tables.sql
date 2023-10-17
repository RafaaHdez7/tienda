-- Crear la tabla de Usuarios
CREATE TABLE Usuarios (
    id serial PRIMARY KEY,
    nombre_usuario VARCHAR(50) NOT NULL,
    contrasena VARCHAR(100) NOT NULL,
    rol VARCHAR(20) NOT NULL
);

-- Crear la tabla de Categorías
CREATE TABLE Categorias (
    id serial PRIMARY KEY,
    nombre_categoria VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255)
);

-- Crear la tabla de Productos
CREATE TABLE Productos (
    id serial PRIMARY KEY,
    nombre_producto VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio NUMERIC(10, 2) NOT NULL,
    stock_disponible INT NOT NULL,
    categoria_id INT REFERENCES Categorias(id)
);

-- Crear la tabla de Pedidos
CREATE TABLE Pedidos (
    id serial PRIMARY KEY,
    usuario_id INT REFERENCES Usuarios(id),
    fecha_hora TIMESTAMP NOT NULL,
    estado_pedido VARCHAR(20) NOT NULL
);

-- Crear la tabla de Detalles del Pedido
CREATE TABLE DetallesPedido (
    id serial PRIMARY KEY,
    pedido_id INT REFERENCES Pedidos(id),
    producto_id INT REFERENCES Productos(id),
    cantidad INT NOT NULL,
    precio_unitario NUMERIC(10, 2) NOT NULL
);
