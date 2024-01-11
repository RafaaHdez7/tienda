package backend.rafhergom.tfg.model.dtos;

import lombok.Data;

@Data
public class PedidoDTO {
    private Long id;
    private UsuarioDTO usuarioDTO;
    private NegocioDTO negocioDTO;
    private EstadoPedidoDTO estadoPedidoDTO;
    private String fechaHora;
}
