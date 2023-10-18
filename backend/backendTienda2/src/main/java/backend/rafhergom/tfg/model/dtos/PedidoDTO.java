package backend.rafhergom.tfg.model.dtos;

import lombok.Data;

@Data
public class PedidoDTO {
    private Long id;
    private Long usuarioId;
    private String usuarioNombre;
    private String estadoPedido;
    private String fechaHora;
}
