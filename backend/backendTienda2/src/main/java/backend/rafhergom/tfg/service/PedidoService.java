package backend.rafhergom.tfg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.rafhergom.tfg.model.dtos.PedidoDTO;
import backend.rafhergom.tfg.model.entity.Pedido;
import backend.rafhergom.tfg.repository.PedidoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<PedidoDTO> obtenerTodosLosPedidos() {
        // Implementa la lógica para obtener todos los pedidos y convertirlos a DTOs.
        // Puedes utilizar el repository y el convertidor de DTOs aquí.
        return null;
    }

    public PedidoDTO obtenerPedidoPorId(Long id) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        return pedidoOptional.map(pedido -> {
            PedidoDTO pedidoDTO = new PedidoDTO();
            // Copiar los atributos relevantes de Pedido a PedidoDTO
            pedidoDTO.setEstadoPedido(pedido.getEstadoPedido());
            pedidoDTO.setFechaHora(pedido.getFechaHora().toString());
            pedidoDTO.setUsuarioId(pedido.getUsuario().getId());
            pedidoDTO.setId(pedido.getId());
            pedidoDTO.setUsuarioNombre(pedido.getUsuario().getNombre());
            return pedidoDTO;
        }).orElse(null);
    }

    public PedidoDTO crearPedido(PedidoDTO pedidoDTO) {
        // Implementa la lógica para crear un pedido a partir del DTO.
        // Puedes utilizar el repository y el convertidor de DTOs aquí.
        return null;
    }

    public PedidoDTO actualizarPedido(Long id, PedidoDTO pedidoDTO) {
        // Implementa la lógica para actualizar un pedido a partir del DTO.
        // Puedes utilizar el repository y el convertidor de DTOs aquí.
        return null;
    }

    public void eliminarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }
}
