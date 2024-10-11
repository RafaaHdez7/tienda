package backend.rafhergom.tfg.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import backend.rafhergom.tfg.model.dtos.DetallesPedidoDTO;
import backend.rafhergom.tfg.model.dtos.EstadoPedidoDTO.Estado;
import backend.rafhergom.tfg.model.dtos.PedidoDTO;
import backend.rafhergom.tfg.model.dtos.ProductoDTO;
import backend.rafhergom.tfg.model.dtos.UsuarioDTO;
import backend.rafhergom.tfg.model.entity.Usuario;
import backend.rafhergom.tfg.model.entity.Negocio;
import backend.rafhergom.tfg.model.entity.Pedido;
import backend.rafhergom.tfg.model.entity.Producto;
import backend.rafhergom.tfg.model.dtos.NegocioDTO;
import backend.rafhergom.tfg.service.DetallesPedidoService;
import backend.rafhergom.tfg.service.NegocioService;
import backend.rafhergom.tfg.service.PedidoService;
import backend.rafhergom.tfg.service.ProductoService;
import backend.rafhergom.tfg.service.UsuarioService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;

@RestController
@Api(tags = "PedidoController")
@RequestMapping("/api/pedido")
@Slf4j
public class PedidoController {

	private final PedidoService pedidoService;
	private final NegocioService negocioService;
	private final UsuarioService usuarioService;
	private final DetallesPedidoService detallesPedidoService;
	private final ProductoService productoService;
	private final ModelMapper modelMapper;

	@Autowired
	public PedidoController(PedidoService pedidoService, NegocioService negocioService, UsuarioService usuarioService,
			DetallesPedidoService detallesPedidoService, ModelMapper modelMapper, ProductoService productoService) {
		this.pedidoService = pedidoService;
		this.negocioService = negocioService;
		this.usuarioService = usuarioService;
		this.detallesPedidoService = detallesPedidoService;
		this.modelMapper = modelMapper;
		this.productoService = productoService;

	}

	@GetMapping
	public List<PedidoDTO> obtenerTodosLosPedidos() {
		return pedidoService.obtenerTodosLosPedidos();
	}

	@GetMapping("/{id}")
	public PedidoDTO obtenerPedidoPorId(@PathVariable Long id) {
		return pedidoService.obtenerPedidoPorId(id);
	}

	@GetMapping("/usuario/{id}")
	public List<PedidoDTO> obtenerPedidoPorIdUsuario(@PathVariable Long id) {
		return pedidoService.obtenerPedidosPorIdUsuario(id);
	}

	@PostMapping
    public PedidoDTO crearPedidoPorDetallesPedido(@RequestBody List<DetallesPedidoDTO> detallesPedidoDTO) {
    	Negocio negocio = productoService.obtenerNegocioPorProductoId(detallesPedidoDTO.get(0).getProducto().getId());
    	Usuario usuario = usuarioService.obtenerUsuarioPorNombre(detallesPedidoDTO.get(0).getPedido().getUsuarioDTO().getNombre());
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
    	
        return pedidoDTOsaved;
    }

	@PutMapping("/{id}")
	public PedidoDTO actualizarPedido(@PathVariable Long id, @RequestBody PedidoDTO pedidoDTO) {
		return pedidoService.actualizarPedido(id, pedidoDTO);
	}

	@DeleteMapping("/{id}")
	public void eliminarPedido(@PathVariable Long id) {
		pedidoService.eliminarPedido(id);
	}
}
