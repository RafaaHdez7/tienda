
-- Insertar usuarios
INSERT INTO tfg.Usuario (nombre, contrasena, rol, email ,activo) VALUES
    ('Usuario1', 'contrasena1', 'user', 'rafa@homtmail.com ',true),
    ('Usuario2', 'contrasena2', 'admin', 'rafa@homtmail.com ', true),
    ('Usuario3', 'contrasena3', 'negocio', 'rafa@homtmail.com ', true);

-- Insertar categorías de Producto
INSERT INTO tfg.CategoriaProducto (nombre_categoria, descripcion) VALUES
    ('Comida Rápida', 'Alimentos que se preparan y sirven rápidamente'),
    ('Bebidas', 'Variedad de bebidas refrescantes'),
    ('Postres', 'Deliciosos postres y dulces');

-- Insertar categorías de Negocio
INSERT INTO tfg.CategoriaNegocio (nombre_categoria, descripcion) VALUES
    ('Hamburgueseria', 'Hamburguesas'),
    ('Japonés', 'comida asiática'),
    ('Española', 'Comida tradicional española');

-- Insertar negocios
INSERT INTO tfg.Negocio (nombre, categoria_negocio_id, Descripcion, Link, ImagenURL, usuario_id) VALUES
    ('Burger Place', (SELECT id FROM tfg.CategoriaNegocio WHERE nombre_categoria = 'Hamburgueseria'), 'Ofrecemos las mejores hamburguesas', 'www.burgerplace.com', 'https://cdn.businessinsider.es/sites/navi.axelspringer.es/public/media/image/2023/07/hamburguesa-3096800.jpg?tf=3840x', (SELECT id FROM tfg.Usuario WHERE nombre = 'Usuario1')),
    ('Bebidas Express',(SELECT id FROM tfg.CategoriaNegocio WHERE nombre_categoria = 'Japonés'), 'Variedad de bebidas refrescantes', 'www.bebidasexpress.com', 'https://entrenosotros.consum.es/public/Image/2022/7/refrescos_Mediano.jpg', (SELECT id FROM tfg.Usuario WHERE nombre = 'Usuario1')),
    ('Sushi Express', (SELECT id FROM tfg.CategoriaNegocio WHERE nombre_categoria = 'Japonés'), 'Sushi', 'www.sushiexpress.com', 'https://upload.wikimedia.org/wikipedia/commons/f/f4/Sushi_bandeja.jpg', (SELECT id FROM tfg.Usuario WHERE nombre = 'Usuario1')),
    ('Dulces Delicias', (SELECT id FROM tfg.CategoriaNegocio WHERE nombre_categoria = 'Española'), 'Postres y dulces deliciosos', 'www.dulcesdelicias.com', 'https://entrenosotros.consum.es/public/Image/2022/7/refrescos_Mediano.jpg', (SELECT id FROM tfg.Usuario WHERE nombre = 'Usuario1'));

-- Insertar productos
INSERT INTO tfg.Producto (nombre_producto, descripcion, precio, stock_disponible, categoria_producto_id, negocio_id) VALUES
    ('Hamburguesa', 'Hamburguesa clásica con queso', 5.99, 50, (SELECT id FROM tfg.CategoriaProducto WHERE nombre_categoria = 'Comida Rápida'), (SELECT id FROM tfg.Negocio WHERE nombre = 'Burger Place')),
    ('Refresco', 'Bebida gaseosa de cola', 1.50, 100, (SELECT id FROM tfg.CategoriaProducto WHERE nombre_categoria = 'Bebidas'), (SELECT id FROM tfg.Negocio WHERE nombre = 'Burger Place')),
    ('Pastel de Chocolate', 'Pastel de chocolate con crema', 12.99, 20, (SELECT id FROM tfg.CategoriaProducto WHERE nombre_categoria = 'Postres'),  (SELECT id FROM tfg.Negocio WHERE nombre = 'Burger Place'));

-- Insertar pedidos
INSERT INTO tfg.Pedido (usuario_id, negocio_id, fecha_hora, estado_pedido) VALUES
    ((SELECT id FROM tfg.Usuario WHERE nombre = 'Usuario1'), (SELECT id FROM tfg.Negocio WHERE nombre = 'Bebidas Express'), CURRENT_TIMESTAMP, 'En Proceso'),
    ((SELECT id FROM tfg.Usuario WHERE nombre = 'Usuario2'), (SELECT id FROM tfg.Negocio WHERE nombre = 'Bebidas Express'), CURRENT_TIMESTAMP, 'Entregado'),
    ((SELECT id FROM tfg.Usuario WHERE nombre = 'Usuario3'), (SELECT id FROM tfg.Negocio WHERE nombre = 'Bebidas Express'), CURRENT_TIMESTAMP, 'Enviado');

-- Insertar detalles del pedido
INSERT INTO tfg.DetallePedido (pedido_id, producto_id, cantidad, precio_unitario) VALUES
    ((SELECT id FROM tfg.Pedido WHERE usuario_id = (SELECT id FROM tfg.Usuario WHERE nombre = 'Usuario1') AND negocio_id = (SELECT id FROM tfg.Negocio WHERE nombre = 'Bebidas Express')) , (SELECT id FROM tfg.Producto WHERE nombre_producto = 'Refresco'), 2, 5.99),
    ((SELECT id FROM tfg.Pedido WHERE usuario_id = (SELECT id FROM tfg.Usuario WHERE nombre = 'Usuario1') AND negocio_id = (SELECT id FROM tfg.Negocio WHERE nombre = 'Bebidas Express')) , (SELECT id FROM tfg.Producto WHERE nombre_producto = 'Refresco'), 3, 1.50),
    ((SELECT id FROM tfg.Pedido WHERE usuario_id = (SELECT id FROM tfg.Usuario WHERE nombre = 'Usuario1') AND negocio_id = (SELECT id FROM tfg.Negocio WHERE nombre = 'Bebidas Express')),(SELECT id FROM tfg.Producto WHERE nombre_producto = 'Refresco'), 1, 12.99);