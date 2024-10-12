package backend.rafhergom.tfg.model.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class HistoricoTransaccionesDTO {
    private Long id;
    private UsuarioDTO usuarioDTO; // o UsuarioDTO usuario; si deseas incluir el objeto Usuario
    private String tipoTransaccion; // 'ganancia', 'pérdida', etc.
    private BigDecimal puntos; // Cambiado a BigDecimal para manejar decimales
}