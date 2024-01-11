package backend.rafhergom.tfg.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import backend.rafhergom.tfg.model.dtos.NegocioDTO;
import backend.rafhergom.tfg.model.dtos.ProductoDTO;
import backend.rafhergom.tfg.model.entity.Negocio;
import backend.rafhergom.tfg.model.entity.Producto;
import backend.rafhergom.tfg.repository.ProductoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    
    private final ModelMapper modelMapper;

    @Autowired
    public ProductoService(ProductoRepository productoRepository,
    		@Qualifier("ModelMapperConfig") ModelMapper modelMapper) {
        this.productoRepository = productoRepository;
        this.modelMapper = modelMapper;
    }

    public List<ProductoDTO> obtenerTodosLosProductos() {
        List<Producto> productos = productoRepository.findAll();
        List<ProductoDTO> productoDTOs = new ArrayList<>();

        for (Producto producto : productos) {
            ProductoDTO productoDTO = modelMapper.map(producto, ProductoDTO.class);
            // También puedes configurar otros campos según sea necesario
            productoDTOs.add(productoDTO);
        }

        return productoDTOs;
    }

    public ProductoDTO obtenerProductoPorId(Long id) {
            Optional<Producto> productoOptional = productoRepository.findById(id);
            return productoOptional.map(producto -> {
            	return modelMapper.map(producto, ProductoDTO.class);
            }).orElse(null);
        }

    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
 	   return modelMapper.map(productoRepository.save(modelMapper.map(
 			  productoDTO, Producto.class)), ProductoDTO.class);
   }

    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO) {

              Optional<Producto> productoOptional = productoRepository.findById(id);
              if (productoOptional.isPresent()) {
                  return modelMapper.map(productoRepository.save(productoOptional.get()), ProductoDTO.class);
              } else {
                  // Manejo cuando el negocio con el ID proporcionado no existe
                  return null;
              }
          }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
