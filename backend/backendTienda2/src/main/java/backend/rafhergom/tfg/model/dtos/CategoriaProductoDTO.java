package backend.rafhergom.tfg.model.dtos;

import lombok.Data;

@Data
public class CategoriaProductoDTO {
    private Long id;
    private String nombreCategoria;
    private String descripcion;
    private String imagenURL;

    // Agrega getters y setters según sea necesario
}
