package backend.rafhergom.tfg.model.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class NegocioDTO {
	@JsonAlias({"Id", "id"})
    private Long id;
    @JsonAlias({"CategoriaNegocioId", "categoriaNegocioId"})
    private Long categoriaNegocioId;
    @JsonAlias({"Nombre", "nombre"})
    private String nombre;
    @JsonAlias({"Descripcion", "descripcion"})
    private String descripcion;
    @JsonAlias({"Usuario", "usuario"})
    private UsuarioDTO usuario;
    @JsonAlias({"Link", "link"})
    private String link;
    @JsonAlias({"ImagenURL", "imagenURL"})
    private String imagenURL;
}
