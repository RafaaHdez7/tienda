package backend.rafhergom.tfg.model.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductoDTO {

    private Long id;
    private String nombreProducto;
    private String descripcion;
    private BigDecimal precio;
    private int stockDisponible;
    private CategoriaProductoDTO categoriaProductoDTO;
    private NegocioDTO negocioDTO;
    private String imagenURL;

    // Getters y setters
}
