package backend.rafhergom.tfg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import backend.rafhergom.tfg.model.entity.Usuario;
import backend.rafhergom.tfg.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Tag(name = "UsuarioController", description = "Controlador para gestionar usuarios")
@RequestMapping("/api/usuario")
@Slf4j
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Obtener todos los usuarios", description = "Retorna una lista de todos los usuarios.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente.")
    })
    @GetMapping
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioService.obtenerTodosLosUsuarios();
    }

    @Operation(summary = "Obtener usuario por ID", description = "Retorna el usuario asociado al ID especificado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado."),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado.")
    })
    @GetMapping("/{id}")
    public Usuario obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }
    
    @Operation(summary = "Obtener usuario por nombre", description = "Retorna el usuario asociado al nombre especificado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado."),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado.")
    })
    @GetMapping("/nombre/{nombre}")
    public Usuario obtenerUsuarioPorNombre(@PathVariable String nombre) {
        return usuarioService.obtenerUsuarioPorNombre(nombre);
    }

    @Operation(summary = "Crear un nuevo usuario", description = "Crea un nuevo usuario con los detalles proporcionados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente."),
        @ApiResponse(responseCode = "400", description = "Error en los detalles del usuario.")
    })
    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    @Operation(summary = "Actualizar un usuario", description = "Actualiza el usuario existente con los datos proporcionados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente."),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado.")
    })
    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.actualizarUsuario(id, usuario);
    }

    @Operation(summary = "Eliminar un usuario", description = "Elimina el usuario especificado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente."),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado.")
    })
    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }

    @Operation(summary = "Dar alta a un usuario como negocio", description = "Cambia el rol del usuario especificado a 'negocio'.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado a negocio exitosamente."),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado.")
    })
    @PostMapping("/darAltaNegocio")
    public Usuario darAltaUsuarioComoNegocio(@RequestBody String nombreUsuario) {
        Usuario user = usuarioService.obtenerUsuarioPorNombre(nombreUsuario);
        user.setRol("negocio");
        return usuarioService.actualizarUsuario(user.getId(), user);
    }
}
