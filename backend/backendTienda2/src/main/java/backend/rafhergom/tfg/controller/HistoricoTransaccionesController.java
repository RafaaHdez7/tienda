package backend.rafhergom.tfg.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import backend.rafhergom.tfg.model.dtos.HistoricoTransaccionesDTO;
import backend.rafhergom.tfg.service.DetallesPedidoService;
import backend.rafhergom.tfg.service.HistoricoTransaccionesService;
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
@Tag(name = "HistoricoTransaccionesController", description = "Controlador para gestionar el histórico de transacciones")
@RequestMapping("/api/transacciones")
@Slf4j
public class HistoricoTransaccionesController {

    private final PedidoService pedidoService;
    private final NegocioService negocioService;
    private final UsuarioService usuarioService;
    private final DetallesPedidoService detallesPedidoService;
    private final ProductoService productoService;
    private final ModelMapper modelMapper;
    private final MonederoService monederoService;
    private final HistoricoTransaccionesService historicoTransaccionesService;

    @Autowired
    public HistoricoTransaccionesController(PedidoService pedidoService, NegocioService negocioService,
                                            UsuarioService usuarioService, DetallesPedidoService detallesPedidoService,
                                            ModelMapper modelMapper, ProductoService productoService,
                                            MonederoService monederoService, HistoricoTransaccionesService historicoTransaccionesService) {
        this.pedidoService = pedidoService;
        this.negocioService = negocioService;
        this.usuarioService = usuarioService;
        this.detallesPedidoService = detallesPedidoService;
        this.modelMapper = modelMapper;
        this.productoService = productoService;
        this.monederoService = monederoService;
        this.historicoTransaccionesService = historicoTransaccionesService;
    }

    @Operation(summary = "Obtener todas las transacciones", description = "Retorna una lista de todas las transacciones en el historial.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    @GetMapping
    public List<HistoricoTransaccionesDTO> obtenerTodosLosHistoricoTransacciones() {
        return historicoTransaccionesService.obtenerTodosLosHistoricoTransacciones();
    }

    @Operation(summary = "Crear una nueva transacción", description = "Crea una nueva entrada en el historial de transacciones.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Transacción creada exitosamente")
    })
    @PostMapping
    public HistoricoTransaccionesDTO crearHistoricoTransacciones(@RequestBody HistoricoTransaccionesDTO historicoTransaccionesDTO) {
        HistoricoTransaccionesDTO historico = historicoTransaccionesService.crearHistoricoTransacciones(historicoTransaccionesDTO);
        monederoService.actualizarMonederoPorTransaccion(historico);
        return historico;
    }

    @Operation(summary = "Obtener transacción por ID", description = "Retorna los detalles de una transacción específica por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transacción encontrada"),
        @ApiResponse(responseCode = "404", description = "Transacción no encontrada")
    })
    @GetMapping("/{id}")
    public HistoricoTransaccionesDTO obtenerHistoricoTransaccionesPorId(@PathVariable Long id) {
        return historicoTransaccionesService.obtenerHistoricoTransaccionesPorId(id);
    }

    @Operation(summary = "Obtener transacciones por ID de usuario", description = "Retorna una lista de transacciones realizadas por un usuario específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transacciones encontradas"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/usuario/{id}")
    public List<HistoricoTransaccionesDTO> obtenerHistoricoTransaccionesPorIdUsuario(@PathVariable Long id) {
        return historicoTransaccionesService.obtenerHistoricoTransaccionesPorIdUsuario(id);
    }

    @Operation(summary = "Obtener transacciones por nombre de usuario", description = "Retorna una lista de transacciones filtradas por el nombre del usuario.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transacciones encontradas"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/usuario/nombre/{nombreUsuario}")
    public List<HistoricoTransaccionesDTO> obtenerMonederoPorNombreUsuario(@PathVariable String nombreUsuario) {
        return historicoTransaccionesService.obtenerHistoricoTransaccionesPorNombreUsuario(nombreUsuario);
    }

    @Operation(summary = "Actualizar una transacción", description = "Actualiza los detalles de una transacción existente por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transacción actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Transacción no encontrada")
    })
    @PutMapping("/{id}")
    public HistoricoTransaccionesDTO HistoricoTransacciones(@PathVariable Long id,
            @RequestBody HistoricoTransaccionesDTO historicoTransaccionesDTO) {
        return historicoTransaccionesService.actualizarHistoricoTransacciones(id, historicoTransaccionesDTO);
    }

    @Operation(summary = "Eliminar una transacción", description = "Elimina una transacción específica del historial.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Transacción eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Transacción no encontrada")
    })
    @DeleteMapping("/{id}")
    public void eliminarMonedero(@PathVariable Long id) {
        monederoService.eliminarMonedero(id);
    }
}
