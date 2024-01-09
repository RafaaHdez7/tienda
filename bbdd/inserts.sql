-- Insertar usuarios
INSERT INTO tfg.Usuario (nombre, contrasena, rol) VALUES
    ('Usuario1', 'contrasena1', 'Cliente'),
    ('Usuario2', 'contrasena2', 'Admin'),
    ('Usuario3', 'contrasena3', 'Cliente');

-- Insertar categorías
INSERT INTO tfg.Categoria (nombre_categoria, descripcion) VALUES
    ('Comida Rápida', 'Alimentos que se preparan y sirven rápidamente'),
    ('Bebidas', 'Variedad de bebidas refrescantes'),
    ('Postres', 'Deliciosos postres y dulces');

-- Insertar productos
INSERT INTO tfg.Producto (nombre_producto, descripcion, precio, stock_disponible, categoria_id, negocio_id) VALUES
    ('Hamburguesa', 'Hamburguesa clásica con queso', 5.99, 50, 1, 1),
    ('Refresco', 'Bebida gaseosa de cola', 1.50, 100, 2, 2),
    ('Pastel de Chocolate', 'Pastel de chocolate con crema', 12.99, 20, 3, 3);

-- Insertar pedidos
INSERT INTO tfg.Pedido (usuario_id, negocio_id, fecha_hora, estado_pedido) VALUES
    (1, 1, CURRENT_TIMESTAMP, 'En Proceso'),
    (2, 2, CURRENT_TIMESTAMP, 'Entregado'),
    (3, 3, CURRENT_TIMESTAMP, 'Enviado');

-- Insertar detalles del pedido
INSERT INTO tfg.DetallePedido (pedido_id, producto_id, cantidad, precio_unitario) VALUES
    (1, 1, 2, 5.99),
    (2, 2, 3, 1.50),
    (3, 3, 1, 12.99);

-- Insertar negocios
INSERT INTO tfg.Negocio (nombre, categoria_id, Descripcion, Link, ImagenURL) VALUES
    ('Burger Place', 1, 'Ofrecemos las mejores hamburguesas', 'www.burgerplace.com', 'imagen1.jpg'),
    ('Bebidas Express', 2, 'Variedad de bebidas refrescantes', 'www.bebidasexpress.com', 'imagen2.jpg'),
    ('Dulces Delicias', 3, 'Postres y dulces deliciosos', 'www.dulcesdelicias.com', 'imagen3.jpg');
