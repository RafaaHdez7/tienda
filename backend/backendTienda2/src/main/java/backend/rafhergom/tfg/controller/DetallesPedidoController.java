package backend.rafhergom.tfg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import backend.rafhergom.tfg.model.dtos.DetallesPedidoDTO;
import backend.rafhergom.tfg.service.DetallesPedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@Tag(name = "DetallePedidoController", description = "Controlador para gestionar los detalles de pedido")
@RequestMapping("/api/detalle-pedido")
public class DetallesPedidoController {

    private final DetallesPedidoService detallesPedidoService;

    @Autowired
    public DetallesPedidoController(DetallesPedidoService detallesPedidoService) {
        this.detallesPedidoService = detallesPedidoService;
    }

    @Operation(summary = "Obtener todos los detalles de pedido", description = "Retorna una lista con todos los detalles de pedido disponibles.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    @GetMapping
    public List<DetallesPedidoDTO> obtenerTodosLosDetallesPedido() {
        return detallesPedidoService.obtenerTodosLosDetallesPedido();
    }

    @Operation(summary = "Obtener detalle de pedido por ID", description = "Retorna un detalle de pedido específico según el ID proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle de pedido encontrado"),
        @ApiResponse(responseCode = "404", description = "Detalle de pedido no encontrado")
    })
    @GetMapping("/{id}")
    public DetallesPedidoDTO obtenerDetallePedidoPorId(@PathVariable Long id) {
        return detallesPedidoService.obtenerDetallesPedidoPorId(id);
    }

    @Operation(summary = "Obtener detalles de pedido por ID de pedido", description = "Retorna una lista de detalles de pedido correspondientes al ID de pedido proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalles de pedido encontrados"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @GetMapping("/pedido/{id}")
    public List<DetallesPedidoDTO> obtenerDetallePedidoPorPedidoId(@PathVariable Long id) {
        return detallesPedidoService.obtenerDetallesPedidoPorIdPedido(id);
    }

    @Operation(summary = "Crear un nuevo detalle de pedido", description = "Crea un nuevo detalle de pedido con los datos proporcionados en el cuerpo de la solicitud.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Detalle de pedido creado exitosamente")
    })
    @PostMapping
    public DetallesPedidoDTO crearDetallePedido(@RequestBody DetallesPedidoDTO detallesPedidoDTO) {
        return detallesPedidoService.crearDetallesPedido(detallesPedidoDTO);
    }

    @Operation(summary = "Actualizar un detalle de pedido", description = "Actualiza un detalle de pedido existente con el ID proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle de pedido actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Detalle de pedido no encontrado")
    })
    @PutMapping("/{id}")
    public DetallesPedidoDTO actualizarDetallePedido(@PathVariable Long id, @RequestBody DetallesPedidoDTO detallesPedidoDTO) {
        return detallesPedidoService.actualizarDetallesPedido(id, detallesPedidoDTO);
    }

    @Operation(summary = "Eliminar un detalle de pedido", description = "Elimina el detalle de pedido correspondiente al ID proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Detalle de pedido eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Detalle de pedido no encontrado")
    })
    @DeleteMapping("/{id}")
    public void eliminarDetallePedido(@PathVariable Long id) {
        detallesPedidoService.eliminarDetallesPedido(id);
    }
}
