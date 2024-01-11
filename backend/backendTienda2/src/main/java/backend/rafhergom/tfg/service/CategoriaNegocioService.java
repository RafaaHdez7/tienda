package backend.rafhergom.tfg.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import backend.rafhergom.tfg.model.dtos.CategoriaNegocioDTO;
import backend.rafhergom.tfg.model.dtos.NegocioDTO;
import backend.rafhergom.tfg.model.entity.CategoriaNegocio;
import backend.rafhergom.tfg.model.entity.Negocio;
import backend.rafhergom.tfg.repository.CategoriaNegocioRepository;
import backend.rafhergom.tfg.utilities.ModelMapperConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaNegocioService {
    private final CategoriaNegocioRepository categoriaNegocioRepository;
    
    private final ModelMapper modelMapper;

    @Autowired
    public CategoriaNegocioService(CategoriaNegocioRepository categoriaNegocioRepository,
    		@Qualifier("ModelMapperConfig")  ModelMapper modelMapper) {
        this.categoriaNegocioRepository = categoriaNegocioRepository;
        this.modelMapper = modelMapper;
    }

    public List<CategoriaNegocioDTO> obtenerTodasLasCategoriaNegocios() {
        List<CategoriaNegocio> categoriaNegocios = categoriaNegocioRepository.findAll();
        List<CategoriaNegocioDTO> categoriaNegocioDTOs = new ArrayList<>();

        for (CategoriaNegocio categoriaNegocio : categoriaNegocios) {
            CategoriaNegocioDTO categoriaNegocioDTO = modelMapper.map(categoriaNegocio, CategoriaNegocioDTO.class);
            categoriaNegocioDTOs.add(categoriaNegocioDTO);
        }

        return categoriaNegocioDTOs;
    }

    public CategoriaNegocioDTO obtenerCategoriaNegocioPorId(Long id) {
        return categoriaNegocioRepository.findById(id).map(categoriaNegocio -> {
           return modelMapper.map(categoriaNegocio, CategoriaNegocioDTO.class);
        }).orElse(null);
    }

    public CategoriaNegocioDTO crearCategoriaNegocio(CategoriaNegocioDTO categoriaNegocioDTO) {
    	
        return modelMapper.map(categoriaNegocioRepository.save(modelMapper.map(
        		categoriaNegocioDTO, CategoriaNegocio.class)), CategoriaNegocioDTO.class);
    }

    public CategoriaNegocioDTO actualizarCategoriaNegocio(Long id, CategoriaNegocioDTO categoriaNegocioDTO) {
    	Optional<CategoriaNegocio> categoriaNegocioOptional = categoriaNegocioRepository.findById(id);
        if (categoriaNegocioOptional.isPresent()) {
            return modelMapper.map(categoriaNegocioRepository.save(categoriaNegocioOptional.get()), CategoriaNegocioDTO.class);
        } else {
            // Manejo cuando el negocio con el ID proporcionado no existe
            return null;
        }
    }

    public void eliminarCategoriaNegocio(Long id) {
        categoriaNegocioRepository.deleteById(id);
    }
}
