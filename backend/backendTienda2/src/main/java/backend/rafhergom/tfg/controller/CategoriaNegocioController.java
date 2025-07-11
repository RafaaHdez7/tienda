package backend.rafhergom.tfg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import backend.rafhergom.tfg.model.dtos.CategoriaNegocioDTO;
import backend.rafhergom.tfg.service.CategoriaNegocioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@Tag(name = "CategoriaNegocioController", description = "Controlador para gestionar las categorías de negocio")
@RequestMapping("/api/categoria-negocio")
@Slf4j
public class CategoriaNegocioController {

    private final CategoriaNegocioService categoriaNegocioService;

    @Autowired
    public CategoriaNegocioController(CategoriaNegocioService categoriaNegocioService) {
        this.categoriaNegocioService = categoriaNegocioService;
    }

    @Operation(summary = "Obtener todas las categorías de negocio", description = "Retorna una lista con todas las categorías de negocio disponibles.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    @GetMapping
    public List<CategoriaNegocioDTO> obtenerTodasLasCategoriaNegocios() {
        return categoriaNegocioService.obtenerTodasLasCategoriaNegocios();
    }

    @Operation(summary = "Obtener una categoría de negocio por ID", description = "Retorna una categoría de negocio específica según el ID proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría de negocio encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoría de negocio no encontrada")
    })
    @GetMapping("/{id}")
    public CategoriaNegocioDTO obtenerCategoriaNegocioPorId(@PathVariable Long id) {
        return categoriaNegocioService.obtenerCategoriaNegocioPorId(id);
    }

    @Operation(summary = "Crear una nueva categoría de negocio", description = "Crea una nueva categoría de negocio con los datos proporcionados en el cuerpo de la solicitud.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoría de negocio creada exitosamente")
    })
    @PostMapping
    public CategoriaNegocioDTO crearCategoriaNegocio(@RequestBody CategoriaNegocioDTO categoriaNegocioDTO) {
        return categoriaNegocioService.crearCategoriaNegocio(categoriaNegocioDTO);
    }

    @Operation(summary = "Actualizar una categoría de negocio", description = "Actualiza una categoría de negocio existente con el ID proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría de negocio actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Categoría de negocio no encontrada")
    })
    @PutMapping("/{id}")
    public CategoriaNegocioDTO actualizarCategoriaNegocio(@PathVariable Long id, @RequestBody CategoriaNegocioDTO categoriaNegocioDTO) {
        return categoriaNegocioService.actualizarCategoriaNegocio(id, categoriaNegocioDTO);
    }

    @Operation(summary = "Eliminar una categoría de negocio", description = "Elimina la categoría de negocio correspondiente al ID proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoría de negocio eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Categoría de negocio no encontrada")
    })
    @DeleteMapping("/{id}")
    public void eliminarCategoriaNegocio(@PathVariable Long id) {
        categoriaNegocioService.eliminarCategoriaNegocio(id);
    }
}
