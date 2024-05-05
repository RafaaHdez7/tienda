package backend.rafhergom.tfg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.rafhergom.tfg.model.dtos.LoginRequestDTO;
import backend.rafhergom.tfg.service.AuthService;
import backend.rafhergom.tfg.service.UsuarioService;
import backend.rafhergom.tfg.model.entity.Usuario;
import io.swagger.annotations.Api;


@RestController
@Api(tags = "AuthController")
@RequestMapping("/api/autorizacion")
public class AuthController {
	
	private String ERROR = "-1";
	private String ROL_USUARIO = "user";

    @Autowired
    private AuthService authService;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String token = authService.authenticate(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
        // Devuelve el token al cliente
        return token;
    }
    
    @PostMapping("/registro")
    public String registro(@RequestBody LoginRequestDTO loginRequestDTO) {
    	Usuario nuevoUsuario = new Usuario();
    	nuevoUsuario.setNombre(loginRequestDTO.getUsername());
    	nuevoUsuario.setContrasena(loginRequestDTO.getPassword());
    	nuevoUsuario.setRol(ROL_USUARIO);
        
    	nuevoUsuario = usuarioService.crearUsuario(nuevoUsuario);
    	
    	if (nuevoUsuario.getId()!= null) {
            // Devuelve el token al cliente
            return authService.authenticate(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
    	}
    	else {
    		return ERROR;
    	}
    }
}
