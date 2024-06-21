package backend.rafhergom.tfg.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import backend.rafhergom.tfg.model.dtos.CategoriaProductoDTO;
import backend.rafhergom.tfg.model.dtos.NegocioDTO;
import backend.rafhergom.tfg.model.dtos.ProductoDTO;
import backend.rafhergom.tfg.model.entity.CategoriaProducto;
import backend.rafhergom.tfg.model.entity.Negocio;
import backend.rafhergom.tfg.model.entity.Producto;
import backend.rafhergom.tfg.repository.CategoriaProductoRepository;
import backend.rafhergom.tfg.repository.NegocioRepository;
import backend.rafhergom.tfg.repository.ProductoRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    
    private final NegocioRepository negocioRepository;
    private final CategoriaProductoRepository categoriaProductoRepository;
    
    private final ModelMapper modelMapper;

    @Autowired
    public ProductoService(ProductoRepository productoRepository,NegocioRepository negocioRepository,  CategoriaProductoRepository categoriaProductoRepository,
    		@Qualifier("modelMapper") ModelMapper modelMapper) {
        this.productoRepository = productoRepository;
        this.negocioRepository = negocioRepository;
        this.categoriaProductoRepository = categoriaProductoRepository;
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
    
    public List<ProductoDTO> obtenerProductoPorIdNegocio(Long id) {
        List<Producto> productos = productoRepository.findByNegocioId(id);

        return productos.stream()
                        .map(producto -> modelMapper.map(producto, ProductoDTO.class))
                        .collect(Collectors.toList());
    }

    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
    	Negocio negocio = negocioRepository.findById(productoDTO.getNegocioDTO().getId()).get();
    	productoDTO.setNegocioDTO(modelMapper.map(negocio, NegocioDTO.class));
    	CategoriaProducto catProd = categoriaProductoRepository.findById(productoDTO.getCategoriaProductoDTO().getId()).get();
    	productoDTO.setCategoriaProductoDTO(modelMapper.map(catProd, CategoriaProductoDTO.class));
    	Producto producto = modelMapper.map( productoDTO, Producto.class);

    	 // Establecer campos de auditoría
        Date fechaActual = new Date();
        producto.setFechaCreacion(fechaActual);
        producto.setFechaModificacion(fechaActual);
        // Aquí deberías establecer los valores correctos para usuarioCreacion y usuarioModificacion,
        // según el contexto de tu aplicación
        producto.setUsuarioCreacion(1L); //TODO
        producto.setUsuarioModificacion(1L); //TODO
        
 	   return modelMapper.map(productoRepository.save(producto), ProductoDTO.class);
   }

    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO) {

              Producto productoLoaded = productoRepository.findById(id).get();
              if (productoLoaded != null) {
            	  Negocio negocio = negocioRepository.findById(productoDTO.getNegocioDTO().getId()).get();
              	productoDTO.setNegocioDTO(modelMapper.map(negocio, NegocioDTO.class));
              	CategoriaProducto catProd = categoriaProductoRepository.findById(productoDTO.getCategoriaProductoDTO().getId()).get();
              	productoDTO.setCategoriaProductoDTO(modelMapper.map(catProd, CategoriaProductoDTO.class));
              	Producto producto = modelMapper.map( productoDTO, Producto.class);
              	producto.setId(id);
              	producto.setFechaCreacion(productoLoaded.getFechaCreacion());
              	producto.setUsuarioCreacion(productoLoaded.getUsuarioCreacion());
              	 // Establecer campos de auditoría
                  Date fechaActual = new Date();
                  producto.setFechaModificacion(fechaActual);
                  // Aquí deberías establecer los valores correctos para usuarioCreacion y usuarioModificacion,
                  // según el contexto de tu aplicación
                  producto.setUsuarioModificacion(1L); //TODO
                  return modelMapper.map(productoRepository.save(producto), ProductoDTO.class);
              } else {
                  // Manejo cuando el negocio con el ID proporcionado no existe
                  return null;
              }
          }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
