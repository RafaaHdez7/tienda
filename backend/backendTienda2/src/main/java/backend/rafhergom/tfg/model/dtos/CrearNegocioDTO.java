package backend.rafhergom.tfg.model.dtos;

import java.util.List;

import lombok.Data;

@Data
/*Genera automáticamente los constructores sin argumentos y con todos los argumentos.
Genera automáticamente los métodos getter y setter para todas las propiedades.
Genera automáticamente los métodos equals, hashCode, y toString.*/
public class CrearNegocioDTO {
    private String nombreUsuario;
    private NegocioDTO negocio;
}