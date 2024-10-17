-- SCHEMA: TFG

-- Crear la tabla de Monedero con campos de auditoría
CREATE TABLE tfg.Monedero (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    saldo_puntos NUMERIC(10, 2) DEFAULT 0.00 NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    usuario_creacion INT,  -- Campo para almacenar el usuario que creó el registro
    usuario_modificacion INT,  -- Campo para almacenar el usuario que modificó el registro
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES tfg.Usuario(id)
);

-- Crear la tabla de HistoricoTransacciones con campos de auditoría
CREATE TABLE tfg.HistoricoTransacciones (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    tipo_transaccion VARCHAR(50) NOT NULL,
    puntos NUMERIC(10, 2) NOT NULL,
    descripcion TEXT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    usuario_creacion INT,  -- Campo para almacenar el usuario que creó la transacción
    usuario_modificacion INT,  -- Campo para almacenar el usuario que modificó la transacción
    CONSTRAINT fk_usuario_transacciones FOREIGN KEY (usuario_id) REFERENCES tfg.Usuario(id)
);
