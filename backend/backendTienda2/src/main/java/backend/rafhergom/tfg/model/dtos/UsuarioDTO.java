package backend.rafhergom.tfg.model.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
/*Genera automáticamente los constructores sin argumentos y con todos los argumentos.
Genera automáticamente los métodos getter y setter para todas las propiedades.
Genera automáticamente los métodos equals, hashCode, y toString.*/
public class UsuarioDTO {
	//@JsonAlias({"Id", "id"})
	private Long id;
	//@JsonAlias({"Nombre", "nombre"})
    private String nombre;
	//@JsonAlias({"Contrasena", "contrasena"})
    private String contrasena;
	//@JsonAlias({"Email", "email"})
    private String email;
	//@JsonAlias({"Rol", "rol"})
    private String rol;
	//@JsonAlias({"Activo", "activo"})
    private Boolean activo = true;
}