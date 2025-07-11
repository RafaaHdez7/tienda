package backend.rafhergom.tfg.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import backend.rafhergom.tfg.model.dtos.MonederoDTO;
import backend.rafhergom.tfg.service.DetallesPedidoService;
import backend.rafhergom.tfg.service.MonederoService;
import backend.rafhergom.tfg.service.NegocioService;
import backend.rafhergom.tfg.service.PedidoService;
import backend.rafhergom.tfg.service.ProductoService;
import backend.rafhergom.tfg.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@Tag(name = "MonederoController", description = "Controlador para gestionar el monedero de los usuarios")
@RequestMapping("/api/monedero")
@Slf4j
public class MonederoController {

    private final PedidoService pedidoService;
    private final NegocioService negocioService;
    private final UsuarioService usuarioService;
    private final DetallesPedidoService detallesPedidoService;
    private final ProductoService productoService;
    private final ModelMapper modelMapper;
    private final MonederoService monederoService;

    @Autowired
    public MonederoController(PedidoService pedidoService, NegocioService negocioService, UsuarioService usuarioService,
                              DetallesPedidoService detallesPedidoService, ModelMapper modelMapper,
                              ProductoService productoService, MonederoService monederoService) {
        this.pedidoService = pedidoService;
        this.negocioService = negocioService;
        this.usuarioService = usuarioService;
        this.detallesPedidoService = detallesPedidoService;
        this.modelMapper = modelMapper;
        this.productoService = productoService;
        this.monederoService = monederoService;
    }

    @Operation(summary = "Obtener todos los monederos", description = "Retorna una lista de todos los monederos disponibles.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de monederos obtenida correctamente")
    })
    @GetMapping
    public List<MonederoDTO> obtenerTodosLosMonederos() {
        return monederoService.obtenerTodosLosMonederos();
    }

    @Operation(summary = "Obtener monedero por ID", description = "Retorna el monedero asociado al ID especificado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Monedero encontrado"),
        @ApiResponse(responseCode = "404", description = "Monedero no encontrado")
    })
    @GetMapping("/{id}")
    public MonederoDTO obtenerMonederoPorId(@PathVariable Long id) {
        return monederoService.obtenerMonederoPorId(id);
    }

    @Operation(summary = "Obtener monedero por ID de usuario", description = "Retorna el monedero asociado al usuario especificado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Monedero encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/usuario/{id}")
    public MonederoDTO obtenerPedidoPorIdUsuario(@PathVariable Long id) {
        return monederoService.obtenerMonederoPorIdUsuario(id);
    }
    
    @Operation(summary = "Obtener monedero por nombre de usuario", description = "Retorna el monedero asociado al nombre de usuario especificado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Monedero encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/usuario/nombre/{nombreUsuario}")
    public MonederoDTO obtenerMonederoPorNombreUsuario(@PathVariable String nombreUsuario) {
        return monederoService.obtenerPedidosPorNombreUsuario(nombreUsuario);
    }

    @Operation(summary = "Actualizar un monedero", description = "Actualiza el monedero existente con los datos proporcionados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Monedero actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Monedero no encontrado")
    })
    @PutMapping("/{id}")
    public MonederoDTO actualizarMonedero(@PathVariable Long id, @RequestBody MonederoDTO monederoDTO) {
        return monederoService.actualizarMonedero(id, monederoDTO);
    }

    @Operation(summary = "Eliminar un monedero", description = "Elimina el monedero especificado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Monedero eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Monedero no encontrado")
    })
    @DeleteMapping("/{id}")
    public void eliminarMonedero(@PathVariable Long id) {
        monederoService.eliminarMonedero(id);
    }
}
