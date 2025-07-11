package backend.rafhergom.tfg.model.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DetallesPedidoDTO {
    private Long id;
    private Long pedidoId;
    private PedidoDTO pedido;
    private ProductoDTO producto;
    private Long productoId;
    private int cantidad;
    private BigDecimal precioUnitario;
}
