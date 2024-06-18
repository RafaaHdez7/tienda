package backend.rafhergom.tfg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import backend.rafhergom.tfg.model.dtos.NegocioDTO;
import backend.rafhergom.tfg.model.entity.Negocio;
import backend.rafhergom.tfg.repository.NegocioRepository;

@Service
public class NegocioService {
  private final NegocioRepository negocioRepository;
    
    private final ModelMapper modelMapper;

    @Autowired
    public NegocioService(NegocioRepository negocioRepository,
    		@Qualifier("modelMapper") ModelMapper modelMapper) {
        this.negocioRepository = negocioRepository;
        this.modelMapper = modelMapper;
    }

    public List<NegocioDTO> obtenerTodosLosNegocios() {
        List<Negocio> negocios = negocioRepository.findAll();
        List<NegocioDTO> negocioDTOs = new ArrayList<>();

        for (Negocio negocio : negocios) {
            // Utiliza el ModelMapper para mapear de Negocio a NegocioDTO
            NegocioDTO negocioDTO = modelMapper.map(negocio, NegocioDTO.class);
            negocioDTOs.add(negocioDTO);
        }

        return negocioDTOs;
    }

    public NegocioDTO obtenerNegocioPorId(Long id) {
        Optional<Negocio> negocioOptional = negocioRepository.findById(id);
        return negocioOptional.map(negocio -> {
        	return modelMapper.map(negocio, NegocioDTO.class);
        }).orElse(null);
    }
    
    public List<NegocioDTO> obtenerNegocioPorUsuarioId(Long idUsuario) {
        List<Negocio> negocios = negocioRepository.findByUsuarioId(idUsuario);
        List<NegocioDTO> negocioDTOs = new ArrayList<>();

        for (Negocio negocio : negocios) {
            NegocioDTO negocioDTO = modelMapper.map(negocio, NegocioDTO.class);
            negocioDTOs.add(negocioDTO);
        }

        return negocioDTOs;
    }

    public NegocioDTO crearNegocio(NegocioDTO negocioDTO) {
        // Mapeo NegocioDTO a Negocio
        Negocio negocio = modelMapper.map(negocioDTO, Negocio.class);

        // Establecer campos de auditoría
        Date fechaActual = new Date();
        negocio.setFechaCreacion(fechaActual);
        negocio.setFechaModificacion(fechaActual);
        // Aquí deberías establecer los valores correctos para usuarioCreacion y usuarioModificacion,
        // según el contexto de tu aplicación
        negocio.setUsuarioCreacion(1L); //TODO
        negocio.setUsuarioModificacion(1L); //TODO

        // Guardar Negocio y mapear de nuevo a NegocioDTO para retornarlo
        return modelMapper.map(negocioRepository.save(negocio), NegocioDTO.class);
    }
        public NegocioDTO actualizarNegocio(Long id, NegocioDTO negocioDTO) {
            Optional<Negocio> negocioOptional = negocioRepository.findById(id);
         // Establecer campos de auditoría
            Date fechaActual = new Date();
            negocioOptional.get().setFechaModificacion(fechaActual);
            negocioOptional.get().setUsuarioModificacion(1L);//TODO
            if (negocioOptional.isPresent()) {
                return modelMapper.map(negocioRepository.save(negocioOptional.get()), NegocioDTO.class);
            } else {
                // Manejo cuando el negocio con el ID proporcionado no existe
                return null;
            }
        }


    public void eliminarNegocio(Long id) {
        negocioRepository.deleteById(id);
    }
}
