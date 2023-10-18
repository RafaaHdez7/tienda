package rafhergom.backend.tfg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rafhergom.backend.tfg.model.dtos.UsuarioDTO;
import rafhergom.backend.tfg.service.UsuarioService;
import rafhergom.backend.tfg.shared.UsuarioConverter;

@RestController
@RequestMapping(path = "/user")
public class UsuarioController {

    @Autowired
    protected UsuarioService service;

    @Autowired
    protected UsuarioConverter converter;

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPublicUser(@PathVariable Long id) {
        UsuarioDTO dto = this.service.findById(id);
        if (dto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Obtener todos los usuarios
    @GetMapping("/all")
    public ResponseEntity<List<UsuarioDTO>> getAllUsers() {
        List<UsuarioDTO> userDTOs = this.service.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(userDTOs);
    }

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UsuarioDTO userDTO) {
    	UsuarioDTO createdUser = this.service.save(userDTO);
        if (createdUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el usuario");
        }
    }

    // Actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UsuarioDTO userDTO) {
    	UsuarioDTO updatedUser = this.service.edit(userDTO);
        if (updatedUser != null) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    // Eliminar un usuario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

/* La anotación @RestController indica que esta clase es un controlador REST y
 *  simplifica la escritura de controladores REST en Spring. La anotación @Slf4j 
 *  es de Lombok y agrega un logger de registro SLF4J al controlador, lo que te permite 
 *  realizar un registro más eficaz de eventos y mensajes dentro del controlador.
 *   Ambas anotaciones son útiles en un controlador Spring, especialmente cuando deseas exponer
 *    servicios web REST y realizar un seguimiento de las actividades del controlador.*/