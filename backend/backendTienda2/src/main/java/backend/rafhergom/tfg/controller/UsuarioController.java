package backend.rafhergom.tfg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.rafhergom.tfg.model.entity.Usuario;
import backend.rafhergom.tfg.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/usuarios")
@Slf4j // Agrega anotación Lombok para el registro
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioService.obtenerTodosLosUsuarios();
    }

    @GetMapping("/{id}")
    public Usuario obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.actualizarUsuario(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }
}
/* La anotación @RestController indica que esta clase es un controlador REST y
 *  simplifica la escritura de controladores REST en Spring. La anotación @Slf4j 
 *  es de Lombok y agrega un logger de registro SLF4J al controlador, lo que te permite 
 *  realizar un registro más eficaz de eventos y mensajes dentro del controlador.
 *   Ambas anotaciones son útiles en un controlador Spring, especialmente cuando deseas exponer
 *    servicios web REST y realizar un seguimiento de las actividades del controlador.*/