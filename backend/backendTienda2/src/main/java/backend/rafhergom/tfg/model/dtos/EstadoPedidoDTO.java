package backend.rafhergom.tfg.model.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Data;

@Data
public class EstadoPedidoDTO {

    public enum Estado {
        NO_REALIZADO(1),
        EN_PROCESO(2),
        ESPERANDO_REPARTIDOR(3),
        RECOGIDO_REPARTIDOR(4),
        ENTREGADO(5),
        CANCELADO(-1);

        private final int codigo;

        Estado(int codigo) {
            this.codigo = codigo;
        }

        @JsonValue
        public int getCodigo() {
            return codigo;
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
    }
}