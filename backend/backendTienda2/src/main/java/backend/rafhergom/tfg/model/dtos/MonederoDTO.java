package backend.rafhergom.tfg.model.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MonederoDTO {
    private Long id;
    private UsuarioDTO usuarioDTO;
    private BigDecimal saldoPuntos; 
}