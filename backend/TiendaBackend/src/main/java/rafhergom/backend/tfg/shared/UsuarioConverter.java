package rafhergom.backend.tfg.shared;
import org.springframework.stereotype.Service;

import rafhergom.backend.tfg.model.dtos.UsuarioDTO;
import rafhergom.backend.tfg.model.entity.Usuario;

@Service
public class UsuarioConverter extends AbstractDtoConverter<Usuario, UsuarioDTO> {

    @Override
    public Usuario fromDto(UsuarioDTO dto) {
        Usuario Usuario = new Usuario();
        Usuario.setNombre(dto.getNombre());
        Usuario.setRol(dto.getRol());
        Usuario.setContrasena(dto.getContrasena());
        return Usuario;
    }

    @Override
    public UsuarioDTO fromEntity(Usuario entity) {
        UsuarioDTO UsuarioDTO = new UsuarioDTO();
        UsuarioDTO.setNombre(entity.getNombre());
        UsuarioDTO.setRol(entity.getRol());
        UsuarioDTO.setContrasena(entity.getContrasena());
        return UsuarioDTO;
    }
}
