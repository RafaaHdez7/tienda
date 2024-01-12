package backend.rafhergom.tfg.model.dtos;

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

        public int getCodigo() {
            return codigo;
        }
    }
}
