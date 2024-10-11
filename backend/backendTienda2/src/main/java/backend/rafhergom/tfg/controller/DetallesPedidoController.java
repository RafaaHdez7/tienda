package backend.rafhergom.tfg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import backend.rafhergom.tfg.model.dtos.DetallesPedidoDTO;
import backend.rafhergom.tfg.service.DetallesPedidoService;
import io.swagger.annotations.Api;

import java.util.List;

@RestController
@Api(tags = "DetallePedidoController")
@RequestMapping("/api/detalle-pedido")
public class DetallesPedidoController {

    private final DetallesPedidoService detallesPedidoService;

    @Autowired
    public DetallesPedidoController(DetallesPedidoService detallesPedidoService) {
        this.detallesPedidoService = detallesPedidoService;
    }

    @GetMapping
    public List<DetallesPedidoDTO> obtenerTodosLosDetallesPedido() {
        return detallesPedidoService.obtenerTodosLosDetallesPedido();
    }

    @GetMapping("/{id}")
    public DetallesPedidoDTO obtenerDetallePedidoPorId(@PathVariable Long id) {
        return detallesPedidoService.obtenerDetallesPedidoPorId(id);
    }
    @GetMapping("/pedido/{id}")
    public List<DetallesPedidoDTO> obtenerDetallePedidoPorPedidoId(@PathVariable Long id) {
        return detallesPedidoService.obtenerDetallesPedidoPorIdPedido(id);
    }
    
//    @GetMapping("/pedido/{id}")
//    public DetallesPedidoDTO obtenerDetallePedidoPorPedidoId(@PathVariable Long id) {
//        return detallesPedidoService.obtenerDetallesPedidoPorIdPedido(id);
//    }


    @PostMapping
    public DetallesPedidoDTO crearDetallePedido(@RequestBody DetallesPedidoDTO detallesPedidoDTO) {
        return detallesPedidoService.crearDetallesPedido(detallesPedidoDTO);
    }

    @PutMapping("/{id}")
    public DetallesPedidoDTO actualizarDetallePedido(@PathVariable Long id, @RequestBody DetallesPedidoDTO detallesPedidoDTO) {
        return detallesPedidoService.actualizarDetallesPedido(id, detallesPedidoDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminarDetallePedido(@PathVariable Long id) {
        detallesPedidoService.eliminarDetallesPedido(id);
    }
}
