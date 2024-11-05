package backend.rafhergom.tfg.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import backend.rafhergom.tfg.model.dtos.DetallesPedidoDTO;
import backend.rafhergom.tfg.model.dtos.EstadoPedidoDTO.Estado;
import backend.rafhergom.tfg.model.dtos.HistoricoTransaccionesDTO;
import backend.rafhergom.tfg.model.dtos.MonederoDTO;
import backend.rafhergom.tfg.model.dtos.PedidoDTO;
import backend.rafhergom.tfg.model.dtos.ProductoDTO;
import backend.rafhergom.tfg.model.entity.Usuario;
import backend.rafhergom.tfg.model.entity.Monedero;
import backend.rafhergom.tfg.model.entity.Negocio;
import backend.rafhergom.tfg.model.entity.Pedido;
import backend.rafhergom.tfg.service.DetallesPedidoService;
import backend.rafhergom.tfg.service.HistoricoTransaccionesService;
import backend.rafhergom.tfg.service.MonederoService;
import backend.rafhergom.tfg.service.NegocioService;
import backend.rafhergom.tfg.service.PedidoService;
import backend.rafhergom.tfg.service.ProductoService;
import backend.rafhergom.tfg.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Tag(name = "PedidoController", description = "Controlador para gestionar pedidos")
@RequestMapping("/api/pedido")
@Slf4j
public class PedidoController {

    private final PedidoService pedidoService;
    private final MonederoService monederoService;
    private final UsuarioService usuarioService;
    private final DetallesPedidoService detallesPedidoService;
    private final HistoricoTransaccionesService historicoTransaccionesService;
    private final ProductoService productoService;
    private final ModelMapper modelMapper;

    @Autowired
    public PedidoController(PedidoService pedidoService, MonederoService monederoService, UsuarioService usuarioService,
                            DetallesPedidoService detallesPedidoService, ModelMapper modelMapper, ProductoService productoService, HistoricoTransaccionesService historicoTransaccionesService) {
        this.pedidoService = pedidoService;
        this.monederoService = monederoService;
        this.usuarioService = usuarioService;
        this.detallesPedidoService = detallesPedidoService;
        this.modelMapper = modelMapper;
        this.productoService = productoService;
        this.historicoTransaccionesService = historicoTransaccionesService;
    }

    @Operation(summary = "Obtener todos los pedidos", description = "Retorna una lista de todos los pedidos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida correctamente.")
    })
    @GetMapping
    public List<PedidoDTO> obtenerTodosLosPedidos() {
        return pedidoService.obtenerTodosLosPedidos();
    }

    @Operation(summary = "Obtener pedido por ID", description = "Retorna el pedido asociado al ID especificado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado."),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado.")
    })
    @GetMapping("/{id}")
    public PedidoDTO obtenerPedidoPorId(@PathVariable Long id) {
        return pedidoService.obtenerPedidoPorId(id);
    }

    @Operation(summary = "Obtener pedidos por ID de usuario", description = "Retorna los pedidos asociados al ID de usuario especificado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de pedidos encontrada."),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado.")
    })
    @GetMapping("/usuario/{id}")
    public List<PedidoDTO> obtenerPedidoPorIdUsuario(@PathVariable Long id) {
        return pedidoService.obtenerPedidosPorIdUsuario(id);
    }
    
    @Operation(summary = "Obtener pedidos por nombre de usuario", description = "Retorna los pedidos asociados al nombre de usuario especificado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de pedidos encontrada."),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado.")
    })
    @GetMapping("/usuario/nombre/{nombreUsuario}")
    public List<PedidoDTO> obtenerPedidoPorNombreUsuario(@PathVariable String nombreUsuario) {
        return pedidoService.obtenerPedidosPorNombreUsuario(nombreUsuario);
    }

    @Operation(summary = "Crear un nuevo pedido", description = "Crea un nuevo pedido con los detalles proporcionados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente."),
        @ApiResponse(responseCode = "400", description = "Error en los detalles del pedido.")
    })
    @PostMapping
    public PedidoDTO crearPedidoPorDetallesPedido(@RequestBody List<DetallesPedidoDTO> detallesPedidoDTO) {
        Negocio negocio = productoService.obtenerNegocioPorProductoId(detallesPedidoDTO.get(0).getProducto().getId());
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(detallesPedidoDTO.get(0).getPedido().getUsuarioDTO().getNombre());
        MonederoDTO monederoDTO = monederoService.obtenerPedidosPorNombreUsuario(usuario.getNombre());
        BigDecimal total = detallesPedidoService.calcularPrecioTotalPorListDetallesPedidoDTO(detallesPedidoDTO);
        
        if (monederoDTO != null && monederoDTO.getSaldoPuntos().compareTo(total) > 0) {
            Pedido pedido = new Pedido();
            pedido.setNegocio(negocio);
            pedido.setUsuario(usuario);
            Date date = new Date();
            pedido.setFechaCreacion(date);
            pedido.setUsuarioCreacion(usuario.getId());
            PedidoDTO pedidoDTOsaved = pedidoService.crearPedido(pedido);
            pedidoDTOsaved.setEstadoPedidoDTO(Estado.EN_PROCESO);
            for (DetallesPedidoDTO dpDTO : detallesPedidoDTO) {
                dpDTO.setPedido(pedidoDTOsaved);
                ProductoDTO productoDTO = productoService.obtenerProductoPorId(dpDTO.getProducto().getId());
                dpDTO.setProducto(productoDTO);
                detallesPedidoService.crearDetallesPedido(dpDTO);
            }

            HistoricoTransaccionesDTO transaccionClienteDTO = historicoTransaccionesService.crearHistoricoTransaccionesPorPedido(pedidoDTOsaved, true);
            HistoricoTransaccionesDTO transaccionNegocioDTO = historicoTransaccionesService.crearHistoricoTransaccionesPorPedido(pedidoDTOsaved, false);
            monederoService.actualizarMonederoPorTransaccion(transaccionClienteDTO);
            monederoService.actualizarMonederoPorTransaccion(transaccionNegocioDTO);

            return pedidoDTOsaved;
        } else {
            return null;
        }
    }

    @Operation(summary = "Actualizar un pedido", description = "Actualiza el pedido existente con los datos proporcionados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido actualizado exitosamente."),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado.")
    })
    @PutMapping("/{id}")
    public PedidoDTO actualizarPedido(@PathVariable Long id, @RequestBody PedidoDTO pedidoDTO) {
        return pedidoService.actualizarPedido(id, pedidoDTO);
    }

    @Operation(summary = "Eliminar un pedido", description = "Elimina el pedido especificado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pedido eliminado exitosamente."),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado.")
    })
    @DeleteMapping("/{id}")
    public void eliminarPedido(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
    }
}
