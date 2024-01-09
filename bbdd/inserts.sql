-- Insertar usuarios
INSERT INTO tfg.Usuario (nombre, contrasena, rol) VALUES
    ('Usuario1', 'contrasena1', 'Cliente'),
    ('Usuario2', 'contrasena2', 'Admin'),
    ('Usuario3', 'contrasena3', 'Cliente');

-- Insertar categorías
INSERT INTO tfg.CategoriaProducto (nombre_categoria, descripcion) VALUES
    ('Comida Rápida', 'Alimentos que se preparan y sirven rápidamente'),
    ('Bebidas', 'Variedad de bebidas refrescantes'),
    ('Postres', 'Deliciosos postres y dulces');
	
	-- Insertar categorías
INSERT INTO tfg.CategoriaNegocio (nombre_categoria, descripcion) VALUES
    ('Hamburgueseria', 'Hamburguesas'),
    ('Japonés', 'comida asiática'),
    ('Española', 'Comida tradicional española');
	
-- Insertar negocios
INSERT INTO tfg.Negocio (nombre, categoria_negocio_id, Descripcion, Link, ImagenURL) VALUES
    ('Burger Place', (select id from tfg.CategoriaNegocio where nombre_categoria = 'Hamburgueseria'), 'Ofrecemos las mejores hamburguesas', 'www.burgerplace.com', 'imagen1.jpg'),
    ('Bebidas Express',(select id from tfg.CategoriaNegocio where nombre_categoria = 'Japonés'), 'Variedad de bebidas refrescantes', 'www.bebidasexpress.com', 'imagen2.jpg'),
    ('Dulces Delicias', (select id from tfg.CategoriaNegocio where nombre_categoria = 'Española'), 'Postres y dulces deliciosos', 'www.dulcesdelicias.com', 'imagen3.jpg');


-- Insertar productos
INSERT INTO tfg.Producto (nombre_producto, descripcion, precio, stock_disponible, categoria_producto_id, negocio_id) VALUES
    ('Hamburguesa', 'Hamburguesa clásica con queso', 5.99, 50, (select id from tfg.CategoriaProducto where nombre_categoria = 'Comida Rápida'), (select id from tfg.Negocio where nombre = 'Burger Place')),
    ('Refresco', 'Bebida gaseosa de cola', 1.50, 100, (select id from tfg.CategoriaProducto where nombre_categoria = 'Bebidas'), (select id from tfg.Negocio where nombre = 'Bebidas Express')),
    ('Pastel de Chocolate', 'Pastel de chocolate con crema', 12.99, 20, (select id from tfg.CategoriaProducto where nombre_categoria = 'Postres'),  (select id from tfg.Negocio where nombre = 'Dulces Delicias'));

-- Insertar pedidos
INSERT INTO tfg.Pedido (usuario_id, negocio_id, fecha_hora, estado_pedido) VALUES
    ((select id from tfg.Usuario where nombre = 'Usuario1'), (select id from tfg.Negocio where nombre = 'Bebidas Express'), CURRENT_TIMESTAMP, 'En Proceso'),
    ((select id from tfg.Usuario where nombre = 'Usuario2'), (select id from tfg.Negocio where nombre = 'Bebidas Express'), CURRENT_TIMESTAMP, 'Entregado'),
    ((select id from tfg.Usuario where nombre = 'Usuario3'), (select id from tfg.Negocio where nombre = 'Bebidas Express'), CURRENT_TIMESTAMP, 'Enviado');

-- Insertar detalles del pedido
INSERT INTO tfg.DetallePedido (pedido_id, producto_id, cantidad, precio_unitario) VALUES
    ((select id from tfg.Pedido where usuario_id = (select id from tfg.Usuario where nombre = 'Usuario1') and negocio_id = (select id from tfg.Negocio where nombre = 'Bebidas Express')) , (select id from tfg.Producto where nombre_producto = 'Refresco'), 2, 5.99),
    ((select id from tfg.Pedido where usuario_id = (select id from tfg.Usuario where nombre = 'Usuario1') and negocio_id = (select id from tfg.Negocio where nombre = 'Bebidas Express')), (select id from tfg.Producto where nombre_producto = 'Refresco'), 3, 1.50),
    ((select id from tfg.Pedido where usuario_id = (select id from tfg.Usuario where nombre = 'Usuario1') and negocio_id = (select id from tfg.Negocio where nombre = 'Bebidas Express')),(select id from tfg.Producto where nombre_producto = 'Refresco'), 1, 12.99);
