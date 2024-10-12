package backend.rafhergom.tfg.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import backend.rafhergom.tfg.model.dtos.MonederoDTO;
import backend.rafhergom.tfg.model.dtos.UsuarioDTO;
import backend.rafhergom.tfg.model.entity.Monedero;
import backend.rafhergom.tfg.model.entity.Usuario;
import backend.rafhergom.tfg.repository.MonederoRepository;
import backend.rafhergom.tfg.repository.UsuarioRepository;

@Service
public class MonederoService {

    private final MonederoRepository monederoRepository;
    
    private final UsuarioRepository usuarioRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public MonederoService(MonederoRepository monederoRepository,UsuarioRepository usuarioRepository,
    		@Qualifier("modelMapper") ModelMapper modelMapper) {
        this.monederoRepository = monederoRepository;
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
        
    }

    public List<MonederoDTO> obtenerTodosLosMonederos() {
        List<Monedero> monederos = monederoRepository.findAll();
        List<MonederoDTO> monederosDTO = new ArrayList<>();

        for (Monedero monedero : monederos) {
        	MonederoDTO monederoDTO = modelMapper.map(monedero, MonederoDTO.class);
            // También puedes configurar otros campos según sea necesario
        	monederosDTO.add(monederoDTO);
        }

        return monederosDTO;
    }

    public MonederoDTO obtenerMonederoPorId(Long id) {
            Optional<Monedero> monederoOptional = monederoRepository.findById(id);
            return monederoOptional.map(monedero -> {
            	return modelMapper.map(monedero, MonederoDTO.class);
            }).orElse(null);
        }
    
    public MonederoDTO obtenerMonederoPorIdUsuario(Long id) {
    	Monedero monedero = monederoRepository.findMonederoPorUsuarioId(id);

    	return modelMapper.map(monedero, MonederoDTO.class);
    }
    
    public MonederoDTO obtenerPedidosPorNombreUsuario(String nombre) {
    	Monedero monedero = monederoRepository.findMonederoPorNombreUsuario(nombre);

    	return modelMapper.map(monedero, MonederoDTO.class);
    }

    public MonederoDTO crearMonedero(MonederoDTO monederoDTO) {
    	Usuario usuario = usuarioRepository.findById(monederoDTO.getUsuarioDTO().getId()).get();
    	monederoDTO.setUsuarioDTO(modelMapper.map(usuario, UsuarioDTO.class));

    	Monedero monedero= modelMapper.map( monederoDTO, Monedero.class);

    	 // Establecer campos de auditoría
        Date fechaActual = new Date();
        monedero.setFechaCreacion(fechaActual);
        monedero.setFechaModificacion(fechaActual);
        // Aquí deberías establecer los valores correctos para usuarioCreacion y usuarioModificacion,
        // según el contexto de tu aplicación
        monedero.setUsuarioCreacion(1L); //TODO
        monedero.setUsuarioModificacion(1L); //TODO
        
 	   return modelMapper.map(monederoRepository.save(monedero), MonederoDTO.class);
   }
    
    public MonederoDTO crearMonederoUsuario(Usuario usuario) {
    	MonederoDTO monederoDTO = new MonederoDTO ();
    	monederoDTO.setUsuarioDTO(modelMapper.map(usuario, UsuarioDTO.class));
    	monederoDTO.setSaldoPuntos(BigDecimal.valueOf(0)); 
    	Monedero monedero= modelMapper.map( monederoDTO, Monedero.class);

    	 // Establecer campos de auditoría
        Date fechaActual = new Date();
        monedero.setFechaCreacion(fechaActual);
        monedero.setFechaModificacion(fechaActual);
        // Aquí deberías establecer los valores correctos para usuarioCreacion y usuarioModificacion,
        // según el contexto de tu aplicación
        monedero.setUsuarioCreacion(1L); //TODO
        monedero.setUsuarioModificacion(1L); //TODO
        
 	   return modelMapper.map(monederoRepository.save(monedero), MonederoDTO.class);
   }

    public MonederoDTO actualizarMonedero(Long id, MonederoDTO monederoDTO) {

    		Monedero monederoLoaded = monederoRepository.findById(id).get();
              if (monederoLoaded != null) {
            	  Usuario usuario = usuarioRepository.findById(monederoDTO.getUsuarioDTO().getId()).get();
            	  monederoDTO.setUsuarioDTO(modelMapper.map(usuario, UsuarioDTO.class));
              	Monedero monedero = modelMapper.map( monederoDTO, Monedero.class);
              	monedero.setId(id);
              	monedero.setFechaCreacion(monederoLoaded.getFechaCreacion());
              	monedero.setUsuarioCreacion(monederoLoaded.getUsuarioCreacion());
              	 // Establecer campos de auditoría
                  Date fechaActual = new Date();
                  monedero.setFechaModificacion(fechaActual);
                  // Aquí deberías establecer los valores correctos para usuarioCreacion y usuarioModificacion,
                  // según el contexto de tu aplicación
                  monedero.setUsuarioModificacion(1L); //TODO
                  return modelMapper.map(monederoRepository.save(monedero), MonederoDTO.class);
              } else {
                  // Manejo cuando el negocio con el ID proporcionado no existe
                  return null;
              }
          }

    public void eliminarMonedero(Long id) {
    	monederoRepository.deleteById(id);
    }
}
