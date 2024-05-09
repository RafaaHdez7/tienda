package backend.rafhergom.tfg.model.dtos;

import lombok.Data;

@Data
/*Genera automáticamente los constructores sin argumentos y con todos los argumentos.
Genera automáticamente los métodos getter y setter para todas las propiedades.
Genera automáticamente los métodos equals, hashCode, y toString.*/
public class UsuarioDTO {
	private Long id;
    private String nombre;
    private String contrasena;
    private String email;
    private String rol;
    private Boolean activo;
}