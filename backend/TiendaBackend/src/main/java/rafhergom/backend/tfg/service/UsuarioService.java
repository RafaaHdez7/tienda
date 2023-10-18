package rafhergom.backend.tfg.service;

import org.springframework.stereotype.Service;

import rafhergom.backend.tfg.model.dtos.UsuarioDTO;
import rafhergom.backend.tfg.model.entity.Usuario;
import rafhergom.backend.tfg.model.repository.UsuarioRepository;
import rafhergom.backend.tfg.shared.UsuarioConverter;

@Service
public class UsuarioService extends AbstractBaseService<Usuario, UsuarioDTO, UsuarioConverter, UsuarioRepository, Long> {
	
}