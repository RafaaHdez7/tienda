package backend.rafhergom.tfg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import backend.rafhergom.tfg.repository.UsuarioRepository;
import backend.rafhergom.tfg.security.JwtUtils;


@Service
public class AuthService {
	
	@Autowired
    private UsuarioRepository usuarioRepository;
	private JwtUtils jwtUtils = new JwtUtils(); 
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	// Método para autenticar al usuario
	public String authenticate(String username, String password) {
	    try {
	    	String passwordEncoded = usuarioRepository.findPasswordByUsername(username);

	    	if (passwordEncoded != null && encoder.matches(password, passwordEncoded)) {
	    	    // Autenticación exitosa
	    	    String rol = usuarioRepository.findRoleByUsernameAndPassword(username, passwordEncoded);

	        if (rol != null) {
	            // Verifica que el rol sea válido antes de generar el token
	            if (isValidRole(rol)) {
	                // Generar token JWT
	                return jwtUtils.generateToken(username, rol);
	            } else {
	                // El rol no es válido
	                throw new RuntimeException("Rol no válido");
	            }
	        } else {
	            // Las credenciales no son válidas
	            throw new RuntimeException("Credenciales no válidas");
	        }
	    	}
	    	else {
	    		// Las credenciales no son válidas
	            throw new RuntimeException("Usuario no válido");
	    	}
	    } catch (Exception e) {
	        // Manejar errores de conexión o consultas JPA
	        throw new RuntimeException("Error al autenticar al usuario", e);
	    }
	}



    private boolean isValidRole(String role) {
        // Verifica que el rol sea uno de los roles permitidos
        return role.equals("admin") || role.equals("user") || role.equals("negocio");
    }
}