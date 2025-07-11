-- Modificar la tabla de Usuarios
ALTER TABLE tfg.Usuario
ADD fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD usuario_creacion INT,
ADD usuario_modificacion INT;

-- Modificar la tabla de Categorías de Negocio
ALTER TABLE tfg.CategoriaNegocio
ADD fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD usuario_creacion INT,
ADD usuario_modificacion INT;

-- Modificar la tabla de Categorías de Producto
ALTER TABLE tfg.CategoriaProducto
ADD fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD usuario_creacion INT,
ADD usuario_modificacion INT;

-- Modificar la tabla de Negocios
ALTER TABLE tfg.Negocio
ADD fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD usuario_creacion INT,
ADD usuario_modificacion INT;

-- Modificar la tabla de Productos
ALTER TABLE tfg.Producto
ADD fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD usuario_creacion INT,
ADD usuario_modificacion INT;

-- Modificar la tabla de Pedidos
ALTER TABLE tfg.Pedido
ADD fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD usuario_creacion INT,
ADD usuario_modificacion INT;

-- Modificar la tabla de Detalles del Pedido
ALTER TABLE tfg.DetallePedido
ADD fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD usuario_creacion INT,
ADD usuario_modificacion INT;
