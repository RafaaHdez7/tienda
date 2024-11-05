package backend.rafhergom.tfg.controller;

import java.util.Date;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.rafhergom.tfg.model.dtos.LoginRequestDTO;
import backend.rafhergom.tfg.service.AuthService;
import backend.rafhergom.tfg.service.UsuarioService;
import backend.rafhergom.tfg.model.entity.Usuario;
import backend.rafhergom.tfg.security.JwtUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "AuthController", description = "Controlador para la autenticación y autorización de usuarios")
@RequestMapping("/api/autorizacion")
public class AuthController {
	
	private String ERROR = "-1";
	private String ROL_USUARIO = "user";
	private final JwtUtils jwtUtils;
	
	public AuthController(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }
	
    @Autowired
    private AuthService authService;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    @PermitAll
    public String login(@RequestBody LoginRequestDTO loginRequestDTO) {
    	Usuario usuario = authService.authenticate(loginRequestDTO);
    			String jwtToken = jwtUtils.generateToken(usuario);
    			return jwtToken;
    }
    
    @Operation(summary = "Iniciar sesión", description = "Permite a los usuarios iniciar sesión en la aplicación")
    @GetMapping("/login")
    @PermitAll
    public String registro(@RequestBody LoginRequestDTO loginRequestDTO) {
    	Usuario nuevoUsuario = new Usuario();
    	nuevoUsuario.setNombre(loginRequestDTO.getUsername());
    	nuevoUsuario.setContrasena(loginRequestDTO.getPassword());
    	nuevoUsuario.setEmail(loginRequestDTO.getEmail());
    	nuevoUsuario.setRol(ROL_USUARIO);
    	nuevoUsuario.setFechaCreacion(new Date());
    	nuevoUsuario.setActivo(Boolean.TRUE);
    	nuevoUsuario.setFechaModificacion(new Date());
    	nuevoUsuario = usuarioService.crearUsuario(nuevoUsuario);
    	
    	if (nuevoUsuario.getId()!= null) {
            // Devuelve el token al cliente
    		Usuario usuario = authService.authenticate(loginRequestDTO);
			String jwtToken = jwtUtils.generateToken(usuario);
			return jwtToken;
    	}
    	else {
    		return ERROR;
    	}
    }
}
