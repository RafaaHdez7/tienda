package backend.rafhergom.tfg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import backend.rafhergom.tfg.model.dtos.ProductoDTO;
import backend.rafhergom.tfg.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@CrossOrigin
@Tag(name = "ProductoController", description = "Controlador para gestionar productos")
@RequestMapping("/api/producto")
@Slf4j
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(summary = "Obtener todos los productos", description = "Retorna una lista de todos los productos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente.")
    })
    @GetMapping
    public List<ProductoDTO> obtenerTodosLosProductos() {
        return productoService.obtenerTodosLosProductos();
    }

    @Operation(summary = "Obtener producto por ID", description = "Retorna el producto asociado al ID especificado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado."),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado.")
    })
    @GetMapping("/{id}")
    public ProductoDTO obtenerProductoPorId(@PathVariable Long id) {
        return productoService.obtenerProductoPorId(id);
    }
    
    @Operation(summary = "Obtener productos por ID de negocio", description = "Retorna los productos asociados al ID de negocio especificado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos encontrada."),
        @ApiResponse(responseCode = "404", description = "Negocio no encontrado.")
    })
    @GetMapping("/negocio/{id}")
    public List<ProductoDTO> obtenerProductoPorIdNegocio(@PathVariable Long id) {
        return productoService.obtenerProductoPorIdNegocio(id);
    }

    @Operation(summary = "Crear un nuevo producto", description = "Crea un nuevo producto con los detalles proporcionados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente."),
        @ApiResponse(responseCode = "400", description = "Error en los detalles del producto.")
    })
    @PostMapping
    public ProductoDTO crearProducto(@RequestBody ProductoDTO productoDTO) {
        return productoService.crearProducto(productoDTO);
    }

    @Operation(summary = "Actualizar un producto", description = "Actualiza el producto existente con los datos proporcionados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente."),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado.")
    })
    @PostMapping("/{id}")
    public ProductoDTO actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        return productoService.actualizarProducto(id, productoDTO);
    }

    @Operation(summary = "Eliminar un producto", description = "Elimina el producto especificado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente."),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado.")
    })
    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }
}
