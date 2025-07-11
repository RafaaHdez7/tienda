-- Crear la función que actualiza 'fecha_modificacion'
CREATE OR REPLACE FUNCTION actualizar_fecha_modificacion()
RETURNS TRIGGER AS $$
BEGIN
   NEW.fecha_modificacion = CURRENT_TIMESTAMP;
   RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Crear el trigger para Monedero
CREATE TRIGGER actualizar_fecha_modificacion_monedero
BEFORE UPDATE ON tfg.Monedero
FOR EACH ROW
EXECUTE FUNCTION actualizar_fecha_modificacion();

-- Crear el trigger para HistoricoTransacciones
CREATE TRIGGER actualizar_fecha_modificacion_transacciones
BEFORE UPDATE ON tfg.HistoricoTransacciones
FOR EACH ROW
EXECUTE FUNCTION actualizar_fecha_modificacion();
