-- SCHEMA: TFG

-- DROP SCHEMA IF EXISTS "TFG" ;

CREATE SCHEMA IF NOT EXISTS "tfg"
    AUTHORIZATION postgres;
	
	-- Crear la tabla de Usuarios
CREATE TABLE tfg.Usuario (
    id serial PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    contrasena VARCHAR(200) NOT NULL,
    rol VARCHAR(20) NOT NULL,
	email VARCHAR(200) NOT NULL,
	activo BOOLEAN NOT NULL
);

-- Crear la tabla de Categorías
CREATE TABLE tfg.CategoriaNegocio (
    id serial PRIMARY KEY,
    nombre_categoria VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255),
	imagenURL TEXT
);

-- Crear la tabla de Categorías
CREATE TABLE tfg.CategoriaProducto (
    id serial PRIMARY KEY,
    nombre_categoria VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255),
	imagenURL TEXT
);


-- Crear la tabla de Negocios
CREATE TABLE tfg.Negocio (
    id serial PRIMARY KEY,
    nombre  VARCHAR(100) NOT NULL,
    categoria_negocio_id INT REFERENCES tfg.CategoriaNegocio(id),
    descripcion TEXT,
    link TEXT,
    imagenURL TEXT
);
-- Crear la tabla de Productos
CREATE TABLE tfg.Producto (
    id serial PRIMARY KEY,
    nombre_producto VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio NUMERIC(10, 2) NOT NULL,
    stock_disponible INT NOT NULL,
    categoria_producto_id INT REFERENCES tfg.CategoriaProducto(id),
    negocio_id INT REFERENCES tfg.Negocio(id),
	imagenURL TEXT
);

-- Crear la tabla de Pedidos
CREATE TABLE tfg.Pedido (
    id serial PRIMARY KEY,
    usuario_id INT REFERENCES tfg.Usuario(id),
    negocio_id INT REFERENCES tfg.Negocio(id),
    fecha_hora TIMESTAMP NOT NULL,
    estado_pedido VARCHAR(20) NOT NULL
);

-- Crear la tabla de Detalles del Pedido
CREATE TABLE tfg.DetallePedido (
    id serial PRIMARY KEY,
    pedido_id INT REFERENCES tfg.Pedido(id),
    producto_id INT REFERENCES tfg.Producto(id),
    cantidad INT NOT NULL,
    precio_unitario NUMERIC(10, 2) NOT NULL
);


