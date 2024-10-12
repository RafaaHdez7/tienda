package backend.rafhergom.tfg.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import backend.rafhergom.tfg.model.entity.Usuario;
import backend.rafhergom.tfg.repository.MonederoRepository;
import backend.rafhergom.tfg.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final MonederoService monederoService;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final String ROL_USUARIO = "user";

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, MonederoService monederoService) {
        this.usuarioRepository = usuarioRepository;
		this.monederoService = monederoService;
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        return usuarioOptional.orElse(null);
    }
    public Usuario obtenerUsuarioPorNombre(String nombre) {
    	return usuarioRepository.findByNombre(nombre);

    }

    public Usuario crearUsuario(Usuario usuario) {
    	
    	if (usuarioRepository.findUsuarioPorUsername(usuario.getNombre())  == null ) {
    		usuario.setRol(ROL_USUARIO);
        	usuario.setContrasena(encoder.encode(usuario.getContrasena()));
        	Usuario userCreado = usuarioRepository.save(usuario);
            if (userCreado!= null) {
           	 monederoService.crearMonederoUsuario(userCreado);
            }
            return userCreado;
    	}
    	return null;
    }

    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        if (usuarioRepository.existsById(id)) {
            usuario.setId(id);
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    public void eliminarUsuario(Long id) {
    	//TODO eliminar monedero previamente
        usuarioRepository.deleteById(id);
    }
}
