package backend.rafhergom.tfg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import backend.rafhergom.tfg.model.dtos.ProductoDTO;
import backend.rafhergom.tfg.model.entity.Producto;
import backend.rafhergom.tfg.repository.ProductoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<ProductoDTO> obtenerTodosLosProductos() {
        List<Producto> productos = productoRepository.findAll();
        List<ProductoDTO> productoDTOs = new ArrayList<>();

        for (Producto producto : productos) {
            ProductoDTO productoDTO = new ProductoDTO();
            // Copiar los atributos relevantes de Producto a ProductoDTO
            productoDTO.setId(producto.getId());
            productoDTO.setNombreProducto(producto.getNombreProducto());
            productoDTO.setDescripcion(producto.getDescripcion());
            productoDTO.setPrecio(producto.getPrecio());
            productoDTO.setStockDisponible(producto.getStockDisponible());
            // También puedes configurar otros campos según sea necesario
            productoDTOs.add(productoDTO);
        }

        return productoDTOs;
    }

    public ProductoDTO obtenerProductoPorId(Long id) {
        Optional<Producto> productoOptional = productoRepository.findById(id);
        return productoOptional.map(producto -> {
            ProductoDTO productoDTO = new ProductoDTO();
            // Copiar los atributos relevantes de Producto a ProductoDTO
            productoDTO.setId(producto.getId());
            productoDTO.setNombreProducto(producto.getNombreProducto());
            productoDTO.setDescripcion(producto.getDescripcion());
            productoDTO.setPrecio(producto.getPrecio());
            productoDTO.setStockDisponible(producto.getStockDisponible());
            // También puedes configurar otros campos según sea necesario
            return productoDTO;
        }).orElse(null);
    }

    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        // Crea un nuevo objeto Producto a partir del DTO
        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombreProducto(productoDTO.getNombreProducto());
        nuevoProducto.setDescripcion(productoDTO.getDescripcion());
        nuevoProducto.setPrecio(productoDTO.getPrecio());
        nuevoProducto.setStockDisponible(productoDTO.getStockDisponible());
        // También puedes necesitar configurar otros campos

        // Guarda el nuevo producto en la base de datos utilizando el repository
        nuevoProducto = productoRepository.save(nuevoProducto);

        // Crea un DTO para el nuevo producto y devuélvelo
        ProductoDTO nuevoProductoDTO = new ProductoDTO();
        nuevoProductoDTO.setId(nuevoProducto.getId());
        nuevoProductoDTO.setNombreProducto(nuevoProducto.getNombreProducto());
        nuevoProductoDTO.setDescripcion(nuevoProducto.getDescripcion());
        nuevoProductoDTO.setPrecio(nuevoProducto.getPrecio());
        nuevoProductoDTO.setStockDisponible(nuevoProducto.getStockDisponible());
        // También puedes configurar otros campos según sea necesario

        return nuevoProductoDTO;
    }

    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO) {
        // Verifica si el producto con el ID proporcionado existe en la base de datos
        Optional<Producto> productoOptional = productoRepository.findById(id);
        if (productoOptional.isPresent()) {
            // Obtén el producto existente
            Producto productoExistente = productoOptional.get();

            // Actualiza los campos relevantes del producto existente con los valores del DTO
            productoExistente.setNombreProducto(productoDTO.getNombreProducto());
            productoExistente.setDescripcion(productoDTO.getDescripcion());
            productoExistente.setPrecio(productoDTO.getPrecio());
            productoExistente.setStockDisponible(productoDTO.getStockDisponible());
            // También puedes necesitar actualizar otros campos

            // Guarda la actualización en la base de datos
            productoExistente = productoRepository.save(productoExistente);

            // Crea un DTO para el producto actualizado y devuélvelo
            ProductoDTO productoActualizadoDTO = new ProductoDTO();
            productoActualizadoDTO.setId(productoExistente.getId());
            productoActualizadoDTO.setNombreProducto(productoExistente.getNombreProducto());
            productoActualizadoDTO.setDescripcion(productoExistente.getDescripcion());
            productoActualizadoDTO.setPrecio(productoExistente.getPrecio());
            productoActualizadoDTO.setStockDisponible(productoExistente.getStockDisponible());
            // También puedes configurar otros campos según sea necesario

            return productoActualizadoDTO;
        } else {
            // Si el producto con el ID proporcionado no existe, puedes manejarlo apropiadamente
            return null;
        }
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
