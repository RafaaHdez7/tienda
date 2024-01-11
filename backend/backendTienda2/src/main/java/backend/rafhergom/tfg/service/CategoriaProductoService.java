package backend.rafhergom.tfg.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import backend.rafhergom.tfg.model.dtos.CategoriaProductoDTO;
import backend.rafhergom.tfg.model.dtos.NegocioDTO;
import backend.rafhergom.tfg.model.entity.CategoriaProducto;
import backend.rafhergom.tfg.model.entity.Negocio;
import backend.rafhergom.tfg.repository.CategoriaProductoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaProductoService {
    private final CategoriaProductoRepository categoriaProductoRepository;
    
    private final ModelMapper modelMapper;

    @Autowired
    public CategoriaProductoService(CategoriaProductoRepository categoriaProductoRepository,
    		@Qualifier("ModelMapperConfig") ModelMapper modelMapper) {
        this.categoriaProductoRepository = categoriaProductoRepository;
        this.modelMapper = modelMapper;
    }

    public List<CategoriaProductoDTO> obtenerTodasLasCategoriaProductos() {
        List<CategoriaProducto> categoriaProductos = categoriaProductoRepository.findAll();
        List<CategoriaProductoDTO> categoriaProductoDTOs = new ArrayList<>();

        for (CategoriaProducto categoriaProducto : categoriaProductos) {
            CategoriaProductoDTO categoriaProductoDTO = modelMapper.map(categoriaProducto, CategoriaProductoDTO.class);
            categoriaProductoDTOs.add(categoriaProductoDTO);
        }

        return categoriaProductoDTOs;
    }

    public CategoriaProductoDTO obtenerCategoriaProductoPorId(Long id) {
        return categoriaProductoRepository.findById(id).map(categoriaProducto -> {
           return modelMapper.map(categoriaProducto, CategoriaProductoDTO.class);
        }).orElse(null);
    }

    public CategoriaProductoDTO crearCategoriaProducto(CategoriaProductoDTO categoriaProductoDTO) {
    	
        return modelMapper.map(categoriaProductoRepository.save(modelMapper.map(
        		categoriaProductoDTO, CategoriaProducto.class)), CategoriaProductoDTO.class);
    }

    public CategoriaProductoDTO actualizarCategoriaProducto(Long id, CategoriaProductoDTO categoriaProductoDTO) {
    	Optional<CategoriaProducto> categoriaProductoOptional = categoriaProductoRepository.findById(id);
        if (categoriaProductoOptional.isPresent()) {
            return modelMapper.map(categoriaProductoRepository.save(categoriaProductoOptional.get()), CategoriaProductoDTO.class);
        } else {
            // Manejo cuando el negocio con el ID proporcionado no existe
            return null;
        }
    }

    public void eliminarCategoriaProducto(Long id) {
        categoriaProductoRepository.deleteById(id);
    }
}
