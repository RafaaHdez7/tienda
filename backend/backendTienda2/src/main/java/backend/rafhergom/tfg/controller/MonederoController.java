package backend.rafhergom.tfg.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.rafhergom.tfg.model.dtos.MonederoDTO;
import backend.rafhergom.tfg.model.dtos.PedidoDTO;
import backend.rafhergom.tfg.service.DetallesPedidoService;
import backend.rafhergom.tfg.service.MonederoService;
import backend.rafhergom.tfg.service.NegocioService;
import backend.rafhergom.tfg.service.PedidoService;
import backend.rafhergom.tfg.service.ProductoService;
import backend.rafhergom.tfg.service.UsuarioService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(tags = "MonederoController")
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
			DetallesPedidoService detallesPedidoService, ModelMapper modelMapper, ProductoService productoService, MonederoService monederoService) {
		this.pedidoService = pedidoService;
		this.negocioService = negocioService;
		this.usuarioService = usuarioService;
		this.detallesPedidoService = detallesPedidoService;
		this.modelMapper = modelMapper;
		this.productoService = productoService;
		this.monederoService = monederoService;

	}

	@GetMapping
	public List<MonederoDTO> obtenerTodosLosMonederos() {
		return monederoService.obtenerTodosLosMonederos();
	}

	@GetMapping("/{id}")
	public MonederoDTO obtenerMonederoPorId(@PathVariable Long id) {
		return monederoService.obtenerMonederoPorId(id);
	}

	@GetMapping("/usuario/{id}")
	public MonederoDTO obtenerPedidoPorIdUsuario(@PathVariable Long id) {
		return monederoService.obtenerMonederoPorIdUsuario(id);
	}
	
	@GetMapping("/usuario/nombre/{nombreUsuario}")
	public MonederoDTO obtenerMonederoPorNombreUsuario(@PathVariable String nombreUsuario) {
		return monederoService.obtenerPedidosPorNombreUsuario(nombreUsuario);
	}

	@PutMapping("/{id}")
	public MonederoDTO actualizarMonedero(@PathVariable Long id, @RequestBody MonederoDTO monederoDTO) {
		return monederoService.actualizarMonedero(id, monederoDTO);
	}

	@DeleteMapping("/{id}")
	public void eliminarMonedero(@PathVariable Long id) {
		monederoService.eliminarMonedero(id);
	}
}
