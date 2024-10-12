package backend.rafhergom.tfg.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import backend.rafhergom.tfg.model.dtos.HistoricoTransaccionesDTO;
import backend.rafhergom.tfg.model.dtos.UsuarioDTO;
import backend.rafhergom.tfg.model.entity.HistoricoTransacciones;
import backend.rafhergom.tfg.model.entity.Usuario;
import backend.rafhergom.tfg.repository.HistoricoTransaccionesRepository;
import backend.rafhergom.tfg.repository.MonederoRepository;
import backend.rafhergom.tfg.repository.HistoricoTransaccionesRepository;
import backend.rafhergom.tfg.repository.UsuarioRepository;

@Service
public class HistoricoTransaccionesService {

    private final HistoricoTransaccionesRepository historicoTransaccionesRepository;
    private final MonederoRepository monederoRepository;
    private final UsuarioRepository usuarioRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public HistoricoTransaccionesService(HistoricoTransaccionesRepository historicoTransaccionesRepository, UsuarioRepository usuarioRepository,
    		@Qualifier("modelMapper") ModelMapper modelMapper, MonederoRepository monederoRepository) {
        this.historicoTransaccionesRepository = historicoTransaccionesRepository;
		this.monederoRepository = monederoRepository;
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
        
    }

    public List<HistoricoTransaccionesDTO> obtenerTodosLosHistoricoTransacciones() {
		List<HistoricoTransacciones> historicoTransacciones = historicoTransaccionesRepository.findAll();
        List<HistoricoTransaccionesDTO> historicoTransaccionesListDTO = new ArrayList<>();

        for (HistoricoTransacciones historicoTransaccion : historicoTransacciones) {
        	HistoricoTransaccionesDTO historicoTransaccionesDTO = modelMapper.map(historicoTransaccion, HistoricoTransaccionesDTO.class);
        	historicoTransaccionesListDTO.add(historicoTransaccionesDTO);
        }

        return historicoTransaccionesListDTO;
    }

    public HistoricoTransaccionesDTO obtenerHistoricoTransaccionesPorId(Long id) {
            Optional<HistoricoTransacciones> historicoTransaccionesOptional = historicoTransaccionesRepository.findById(id);
            return historicoTransaccionesOptional.map(historicoTransacciones -> {
            	return modelMapper.map(historicoTransacciones, HistoricoTransaccionesDTO.class);
            }).orElse(null);
        }
    
    public List<HistoricoTransaccionesDTO> obtenerHistoricoTransaccionesPorIdUsuario(Long id) {
    	List<HistoricoTransacciones> historicoTransacciones = historicoTransaccionesRepository.findHistoricoTransaccionesPorUsuarioId(id);
    	List<HistoricoTransaccionesDTO> listDTO = new ArrayList<>();
    	 for (HistoricoTransacciones historicoTransaccion : historicoTransacciones) {
         	HistoricoTransaccionesDTO historicoTransaccionesDTO = modelMapper.map(historicoTransaccion, HistoricoTransaccionesDTO.class);
         	listDTO.add(historicoTransaccionesDTO);
         }
    	 return listDTO;
    }
    
    public List<HistoricoTransaccionesDTO> obtenerHistoricoTransaccionesPorNombreUsuario(String nombre) {
    	List<HistoricoTransacciones> historicoTransacciones = historicoTransaccionesRepository.findHistoricoTransaccionesPorNombreUsuario(nombre);
    	List<HistoricoTransaccionesDTO> listDTO = new ArrayList<>();
    	 for (HistoricoTransacciones historicoTransaccion : historicoTransacciones) {
         	HistoricoTransaccionesDTO historicoTransaccionesDTO = modelMapper.map(historicoTransaccion, HistoricoTransaccionesDTO.class);
         	listDTO.add(historicoTransaccionesDTO);
         }
    	 return listDTO;
    }

    public HistoricoTransaccionesDTO crearHistoricoTransacciones(HistoricoTransaccionesDTO historicoTransaccionesDTO) {
    	Usuario usuario = usuarioRepository.findById(historicoTransaccionesDTO.getUsuarioDTO().getId()).get();
    	historicoTransaccionesDTO.setUsuarioDTO(modelMapper.map(usuario, UsuarioDTO.class));

    	HistoricoTransacciones historicoTransacciones= modelMapper.map( historicoTransaccionesDTO, HistoricoTransacciones.class);

    	 // Establecer campos de auditoría
        Date fechaActual = new Date();
        historicoTransacciones.setFechaCreacion(fechaActual);
        historicoTransacciones.setFechaModificacion(fechaActual);
        // Aquí deberías establecer los valores correctos para usuarioCreacion y usuarioModificacion,
        // según el contexto de tu aplicación
        historicoTransacciones.setUsuarioCreacion(1L); //TODO
        historicoTransacciones.setUsuarioModificacion(1L); //TODO
        
 	   return modelMapper.map(historicoTransaccionesRepository.save(historicoTransacciones), HistoricoTransaccionesDTO.class);
   }

    public HistoricoTransaccionesDTO actualizarHistoricoTransacciones(Long id, HistoricoTransaccionesDTO historicoTransaccionesDTO) {

    		HistoricoTransacciones historicoTransaccionesLoaded = historicoTransaccionesRepository.findById(id).get();
              if (historicoTransaccionesLoaded != null) {
            	  Usuario usuario = usuarioRepository.findById(historicoTransaccionesDTO.getUsuarioDTO().getId()).get();
            	  historicoTransaccionesDTO.setUsuarioDTO(modelMapper.map(usuario, UsuarioDTO.class));
              	HistoricoTransacciones historicoTransacciones = modelMapper.map( historicoTransaccionesDTO, HistoricoTransacciones.class);
              	historicoTransacciones.setId(id);
              	historicoTransacciones.setFechaCreacion(historicoTransaccionesLoaded.getFechaCreacion());
              	historicoTransacciones.setUsuarioCreacion(historicoTransaccionesLoaded.getUsuarioCreacion());
              	 // Establecer campos de auditoría
                  Date fechaActual = new Date();
                  historicoTransacciones.setFechaModificacion(fechaActual);
                  // Aquí deberías establecer los valores correctos para usuarioCreacion y usuarioModificacion,
                  // según el contexto de tu aplicación
                  historicoTransacciones.setUsuarioModificacion(1L); //TODO
                  return modelMapper.map(historicoTransaccionesRepository.save(historicoTransacciones), HistoricoTransaccionesDTO.class);
              } else {
                  // Manejo cuando el negocio con el ID proporcionado no existe
                  return null;
              }
          }

    public void eliminarMonedero(Long id) {
    	historicoTransaccionesRepository.deleteById(id);
    }
}
