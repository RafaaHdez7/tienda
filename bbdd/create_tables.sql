-- Crear la tabla de Usuarios
CREATE TABLE tfg.Usuarios (
    id serial PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    contrasena VARCHAR(100) NOT NULL,
    rol VARCHAR(20) NOT NULL
);

-- Crear la tabla de Categorías
CREATE TABLE tfg.Categorias (
    id serial PRIMARY KEY,
    nombre_categoria VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255)
);

-- Crear la tabla de Productos
CREATE TABLE tfg.Productos (
    id serial PRIMARY KEY,
    nombre_producto VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio NUMERIC(10, 2) NOT NULL,
    stock_disponible INT NOT NULL,
    categoria_id INT REFERENCES tfg.Categorias(id)
);

-- Crear la tabla de Pedidos
CREATE TABLE tfg.Pedidos (
    id serial PRIMARY KEY,
    usuario_id INT REFERENCES tfg.Usuarios(id),
    fecha_hora TIMESTAMP NOT NULL,
    estado_pedido VARCHAR(20) NOT NULL
);

-- Crear la tabla de Detalles del Pedido
CREATE TABLE tfg.DetallesPedido (
    id serial PRIMARY KEY,
    pedido_id INT REFERENCES tfg.Pedidos(id),
    producto_id INT REFERENCES tfg.Productos(id),
    cantidad INT NOT NULL,
    precio_unitario NUMERIC(10, 2) NOT NULL
);
