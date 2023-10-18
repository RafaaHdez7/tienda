package backend.rafhergom.tfg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import backend.rafhergom.tfg.model.dtos.CategoriaDTO;
import backend.rafhergom.tfg.model.entity.Categoria;
import backend.rafhergom.tfg.repository.CategoriaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaDTO> obtenerTodasLasCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        List<CategoriaDTO> categoriaDTOs = new ArrayList<>();

        for (Categoria categoria : categorias) {
            CategoriaDTO categoriaDTO = new CategoriaDTO();
            // Copiar los atributos relevantes de Categoria a CategoriaDTO
            categoriaDTO.setId(categoria.getId());
            categoriaDTO.setNombreCategoria(categoria.getNombreCategoria());
            categoriaDTO.setDescripcion(categoria.getDescripcion());
            categoriaDTOs.add(categoriaDTO);
        }

        return categoriaDTOs;
    }

    public CategoriaDTO obtenerCategoriaPorId(Long id) {
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
        return categoriaOptional.map(categoria -> {
            CategoriaDTO categoriaDTO = new CategoriaDTO();
            // Copiar los atributos relevantes de Categoria a CategoriaDTO
            categoriaDTO.setId(categoria.getId());
            categoriaDTO.setNombreCategoria(categoria.getNombreCategoria());
            categoriaDTO.setDescripcion(categoria.getDescripcion());
            return categoriaDTO;
        }).orElse(null);
    }

    public CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO) {
        // Crea un nuevo objeto Categoria a partir del DTO
        Categoria nuevaCategoria = new Categoria();
        nuevaCategoria.setNombreCategoria(categoriaDTO.getNombreCategoria());
        nuevaCategoria.setDescripcion(categoriaDTO.getDescripcion());

        // Guarda la nueva categoría en la base de datos utilizando el repository
        nuevaCategoria = categoriaRepository.save(nuevaCategoria);

        // Crea un DTO para la nueva categoría y devuélvelo
        CategoriaDTO nuevaCategoriaDTO = new CategoriaDTO();
        nuevaCategoriaDTO.setId(nuevaCategoria.getId());
        nuevaCategoriaDTO.setNombreCategoria(nuevaCategoria.getNombreCategoria());
        nuevaCategoriaDTO.setDescripcion(nuevaCategoria.getDescripcion());

        return nuevaCategoriaDTO;
    }

    public CategoriaDTO actualizarCategoria(Long id, CategoriaDTO categoriaDTO) {
        // Verifica si la categoría con el ID proporcionado existe en la base de datos
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
        if (categoriaOptional.isPresent()) {
            // Obtén la categoría existente
            Categoria categoriaExistente = categoriaOptional.get();

            // Actualiza los campos relevantes de la categoría existente con los valores del DTO
            categoriaExistente.setNombreCategoria(categoriaDTO.getNombreCategoria());
            categoriaExistente.setDescripcion(categoriaDTO.getDescripcion());

            // Guarda la actualización en la base de datos
            categoriaExistente = categoriaRepository.save(categoriaExistente);

            // Crea un DTO para la categoría actualizada y devuélvelo
            CategoriaDTO categoriaActualizadaDTO = new CategoriaDTO();
            categoriaActualizadaDTO.setId(categoriaExistente.getId());
            categoriaActualizadaDTO.setNombreCategoria(categoriaExistente.getNombreCategoria());
            categoriaActualizadaDTO.setDescripcion(categoriaExistente.getDescripcion());

            return categoriaActualizadaDTO;
        } else {
            // Si la categoría con el ID proporcionado no existe, puedes manejarlo apropiadamente
            return null;
        }
    }

    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }
}
