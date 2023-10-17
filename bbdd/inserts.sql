-- Insertar datos de ejemplo en la tabla de Usuarios
INSERT INTO Usuarios (nombre_usuario, contrasena, rol)
VALUES
    ('admin', 'contrasena_admin', 'admin'),
    ('empleado1', 'contrasena_empleado1', 'empleado'),
    ('empleado2', 'contrasena_empleado2', 'empleado'),
    ('cliente1', 'contrasena_cliente1', 'cliente'),
    ('cliente2', 'contrasena_cliente2', 'cliente');

-- Insertar datos de ejemplo en la tabla de Categorías
INSERT INTO Categorias (nombre_categoria, descripcion)
VALUES
    ('Electrónica', 'Productos electrónicos y gadgets'),
    ('Ropa', 'Ropa y accesorios de moda'),
    ('Alimentos', 'Productos alimenticios y comestibles');

-- Insertar datos de ejemplo en la tabla de Productos
INSERT INTO Productos (nombre_producto, descripcion, precio, stock_disponible, categoria_id)
VALUES
    ('Smartphone Modelo X', 'Smartphone de alta gama', 699.99, 50, 1),
    ('Camiseta de Manga Corta', 'Camiseta de algodón', 19.99, 100, 2),
    ('Tablet Android', 'Tablet con pantalla HD', 249.99, 30, 1),
    ('Botas de Senderismo', 'Botas resistentes para exteriores', 79.99, 25, 2),
    ('Chocolate Negro', 'Tableta de chocolate gourmet', 5.99, 200, 3);

-- Insertar datos de ejemplo en la tabla de Pedidos
INSERT INTO Pedidos (usuario_id, fecha_hora, estado_pedido)
VALUES
    (4, CURRENT_TIMESTAMP, 'en proceso'),
    (5, CURRENT_TIMESTAMP, 'enviado');

-- Insertar datos de ejemplo en la tabla de Detalles del Pedido
INSERT INTO DetallesPedido (pedido_id, producto_id, cantidad, precio_unitario)
VALUES
    (1, 1, 2, 699.99),
    (2, 3, 1, 249.99);
