package backend.rafhergom.tfg.model.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

@Data
public class EstadoPedidoDTO {

    public enum Estado {
        NO_REALIZADO(1, "No Realizado"),
        EN_PROCESO(2, "En Proceso"),
        ESPERANDO_REPARTIDOR(3, "Esperando Repartidor"),
        RECOGIDO_REPARTIDOR(4, "Recogido por Repartidor"),
        ENTREGADO(5, "Entregado"),
        CANCELADO(-1, "Cancelado");

        private final int codigo;
        private final String descripcion;

        Estado(int codigo, String descripcion) {
            this.codigo = codigo;
            this.descripcion = descripcion;
        }

        @JsonValue
        public int getCodigo() {
            return codigo;
        }

        public String getDescripcion() {
            return descripcion;
        }

        @JsonCreator
        public static Estado fromCodigo(int codigo) {
            for (Estado estado : Estado.values()) {
                if (estado.getCodigo() == codigo) {
                    return estado;
                }
            }
            throw new IllegalArgumentException("Código de estado desconocido: " + codigo);
        }

        public static Estado fromDescripcion(String descripcion) {
            for (Estado estado : Estado.values()) {
                if (estado.getDescripcion().equalsIgnoreCase(descripcion.trim())) {
                    return estado;
                }
            }
            throw new IllegalArgumentException("Descripción de estado desconocida: " + descripcion);
        }
    }
}
