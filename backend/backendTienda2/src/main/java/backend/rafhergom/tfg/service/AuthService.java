package backend.rafhergom.tfg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import backend.rafhergom.tfg.model.dtos.LoginRequestDTO;
import backend.rafhergom.tfg.model.entity.Usuario;
import backend.rafhergom.tfg.repository.UsuarioRepository;
import backend.rafhergom.tfg.security.JwtUtils;


@Service
public class AuthService {
	
	@Autowired
    private UsuarioRepository usuarioRepository;
	private JwtUtils jwtUtils = new JwtUtils(); 
	private final PasswordEncoder passwordEncoder;
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final AuthenticationManager authenticationManager;
    
    public AuthService(
    		UsuarioRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
        ) {
            this.authenticationManager = authenticationManager;
            this.usuarioRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
        }
    
	

	// Método para autenticar al usuario
    public Usuario authenticate(LoginRequestDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );
        return usuarioRepository.findByNombre(input.getUsername());
    }


    private boolean isValidRole(String role) {
        // Verifica que el rol sea uno de los roles permitidos
        return role.equals("admin") || role.equals("user") || role.equals("negocio");
    }
}