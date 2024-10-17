package backend.rafhergom.tfg.controller;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.rafhergom.tfg.model.dtos.CategoriaNegocioDTO;
import backend.rafhergom.tfg.model.dtos.DetallesPedidoDTO;
import backend.rafhergom.tfg.model.dtos.HistoricoTransaccionesDTO;
import backend.rafhergom.tfg.model.dtos.MonederoDTO;
import backend.rafhergom.tfg.model.dtos.PedidoDTO;
import backend.rafhergom.tfg.model.dtos.ProductoDTO;
import backend.rafhergom.tfg.model.dtos.EstadoPedidoDTO.Estado;
import backend.rafhergom.tfg.model.entity.Negocio;
import backend.rafhergom.tfg.model.entity.Pedido;
import backend.rafhergom.tfg.model.entity.Usuario;
import backend.rafhergom.tfg.model.entity.HistoricoTransacciones;
import backend.rafhergom.tfg.service.DetallesPedidoService;
import backend.rafhergom.tfg.service.HistoricoTransaccionesService;
import backend.rafhergom.tfg.service.MonederoService;
import backend.rafhergom.tfg.service.NegocioService;
import backend.rafhergom.tfg.service.PedidoService;
import backend.rafhergom.tfg.service.ProductoService;
import backend.rafhergom.tfg.service.UsuarioService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(tags = "HistoricoTransaccionesController")
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
			UsuarioService usuarioService, DetallesPedidoService detallesPedidoService, ModelMapper modelMapper,
			ProductoService productoService, MonederoService monederoService,
			HistoricoTransaccionesService historicoTransaccionesService) {
		this.pedidoService = pedidoService;
		this.negocioService = negocioService;
		this.usuarioService = usuarioService;
		this.detallesPedidoService = detallesPedidoService;
		this.modelMapper = modelMapper;
		this.productoService = productoService;
		this.monederoService = monederoService;
		this.historicoTransaccionesService = historicoTransaccionesService;

	}

	@GetMapping
	public List<HistoricoTransaccionesDTO> obtenerTodosLosHistoricoTransacciones() {
		return historicoTransaccionesService.obtenerTodosLosHistoricoTransacciones();
	}

	@PostMapping
	public HistoricoTransaccionesDTO crearHistoricoTransacciones(
			@RequestBody HistoricoTransaccionesDTO historicoTransaccionesDTO) {
		HistoricoTransaccionesDTO historico =  historicoTransaccionesService.crearHistoricoTransacciones(historicoTransaccionesDTO);
		monederoService.actualizarMonederoPorTransaccion(historico);
		return historico;
	}

	@GetMapping("/{id}")
	public HistoricoTransaccionesDTO obtenerHistoricoTransaccionesPorId(@PathVariable Long id) {
		return historicoTransaccionesService.obtenerHistoricoTransaccionesPorId(id);
	}

	@GetMapping("/usuario/{id}")
	public List<HistoricoTransaccionesDTO> obtenerHistoricoTransaccionesPorIdUsuario(@PathVariable Long id) {
		return historicoTransaccionesService.obtenerHistoricoTransaccionesPorIdUsuario(id);
	}

	@GetMapping("/usuario/nombre/{nombreUsuario}")
	public List<HistoricoTransaccionesDTO> obtenerMonederoPorNombreUsuario(@PathVariable String nombreUsuario) {
		return historicoTransaccionesService.obtenerHistoricoTransaccionesPorNombreUsuario(nombreUsuario);
	}

	@PutMapping("/{id}")
	public HistoricoTransaccionesDTO HistoricoTransacciones(@PathVariable Long id,
			@RequestBody HistoricoTransaccionesDTO historicoTransaccionesDTO) {
		return historicoTransaccionesService.actualizarHistoricoTransacciones(id, historicoTransaccionesDTO);
	}

	@DeleteMapping("/{id}")
	public void eliminarMonedero(@PathVariable Long id) {
		monederoService.eliminarMonedero(id);
	}
}
