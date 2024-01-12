package backend.rafhergom.tfg.model.dtos;

import lombok.Data;

@Data
public class NegocioDTO {
    private Long id;
    private CategoriaNegocioDTO categoriaNegocio;
    private String nombre;
    private String descripcion;
    private String link;
    private String imagenURL;
}
