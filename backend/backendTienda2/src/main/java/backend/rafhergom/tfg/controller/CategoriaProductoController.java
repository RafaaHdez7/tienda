package backend.rafhergom.tfg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import backend.rafhergom.tfg.model.dtos.CategoriaProductoDTO;
import backend.rafhergom.tfg.service.CategoriaProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@Tag(name = "CategoriaProductoController", description = "Controlador para gestionar las categorías de producto")
@RequestMapping("/api/categoria-producto")
@Slf4j
public class CategoriaProductoController {

    private final CategoriaProductoService categoriaProductoService;

    @Autowired
    public CategoriaProductoController(CategoriaProductoService categoriaProductoService) {
        this.categoriaProductoService = categoriaProductoService;
    }

    @Operation(summary = "Obtener todas las categorías de producto", description = "Retorna una lista con todas las categorías de producto disponibles.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    @GetMapping
    public List<CategoriaProductoDTO> obtenerTodasLascategoriaProductos() {
        return categoriaProductoService.obtenerTodasLasCategoriaProductos();
    }

    @Operation(summary = "Obtener una categoría de producto por ID", description = "Retorna una categoría de producto específica según el ID proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría de producto encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoría de producto no encontrada")
    })
    @GetMapping("/{id}")
    public CategoriaProductoDTO obtenercategoriaProductoPorId(@PathVariable Long id) {
        return categoriaProductoService.obtenerCategoriaProductoPorId(id);
    }

    @Operation(summary = "Crear una nueva categoría de producto", description = "Crea una nueva categoría de producto con los datos proporcionados en el cuerpo de la solicitud.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoría de producto creada exitosamente")
    })
    @PostMapping
    public CategoriaProductoDTO crearcategoriaProducto(@RequestBody CategoriaProductoDTO CategoriaProductoDTO) {
        return categoriaProductoService.crearCategoriaProducto(CategoriaProductoDTO);
    }

    @Operation(summary = "Actualizar una categoría de producto", description = "Actualiza una categoría de producto existente con el ID proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría de producto actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Categoría de producto no encontrada")
    })
    @PutMapping("/{id}")
    public CategoriaProductoDTO actualizarcategoriaProducto(@PathVariable Long id, @RequestBody CategoriaProductoDTO CategoriaProductoDTO) {
        return categoriaProductoService.actualizarCategoriaProducto(id, CategoriaProductoDTO);
    }

    @Operation(summary = "Eliminar una categoría de producto", description = "Elimina la categoría de producto correspondiente al ID proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoría de producto eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Categoría de producto no encontrada")
    })
    @DeleteMapping("/{id}")
    public void eliminarcategoriaProducto(@PathVariable Long id) {
        categoriaProductoService.eliminarCategoriaProducto(id);
    }
}
