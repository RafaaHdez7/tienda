package backend.rafhergom.tfg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.rafhergom.tfg.model.dtos.PedidoDTO;
import backend.rafhergom.tfg.model.entity.Pedido;
import backend.rafhergom.tfg.model.entity.Usuario;
import backend.rafhergom.tfg.repository.PedidoRepository;
import backend.rafhergom.tfg.repository.UsuarioRepository;

import java.util.ArrayList;
import java.time.LocalDateTime;



import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
	private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;
    

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<PedidoDTO> obtenerTodosLosPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<PedidoDTO> pedidoDTOs = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            PedidoDTO pedidoDTO = new PedidoDTO();
            // Copiar los atributos relevantes de Pedido a PedidoDTO
            pedidoDTO.setEstadoPedido(pedido.getEstadoPedido());
            pedidoDTO.setFechaHora(pedido.getFechaHora().toString());
            pedidoDTO.setUsuarioId(pedido.getUsuario().getId());
            pedidoDTO.setId(pedido.getId());
            pedidoDTO.setUsuarioNombre(pedido.getUsuario().getNombre());
            pedidoDTOs.add(pedidoDTO);
        }

        return pedidoDTOs;
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
        // Crea un nuevo objeto Pedido a partir del DTO
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setEstadoPedido(pedidoDTO.getEstadoPedido());
        nuevoPedido.setFechaHora(LocalDateTime.parse(pedidoDTO.getFechaHora())); // Asegúrate de manejar la conversión apropiadamente
        // Aquí, también necesitarías obtener el usuario correspondiente y asignarlo al pedido
        Usuario usuario = usuarioRepository.findById(pedidoDTO.getUsuarioId()).orElse(null);
        nuevoPedido.setUsuario(usuario);

        // Guarda el nuevo pedido en la base de datos utilizando el repository
        nuevoPedido = pedidoRepository.save(nuevoPedido);

        // Crea un DTO para el nuevo pedido y devuélvelo
        PedidoDTO nuevoPedidoDTO = new PedidoDTO();
        nuevoPedidoDTO.setId(nuevoPedido.getId());
        nuevoPedidoDTO.setEstadoPedido(nuevoPedido.getEstadoPedido());
        nuevoPedidoDTO.setFechaHora(nuevoPedido.getFechaHora().toString());
        // También configura otros campos según sea necesario

        return nuevoPedidoDTO;
    }

    public PedidoDTO actualizarPedido(Long id, PedidoDTO pedidoDTO) {
        // Verifica si el pedido con el ID proporcionado existe en la base de datos
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        if (pedidoOptional.isPresent()) {
            // Obtén el pedido existente
            Pedido pedidoExistente = pedidoOptional.get();

            // Actualiza los campos relevantes del pedido existente con los valores del DTO
            pedidoExistente.setEstadoPedido(pedidoDTO.getEstadoPedido());
            pedidoExistente.setFechaHora(LocalDateTime.parse(pedidoDTO.getFechaHora())); // Asegúrate de manejar la conversión apropiadamente
            // También puedes necesitar actualizar otros campos

            // Guarda la actualización en la base de datos
            pedidoExistente = pedidoRepository.save(pedidoExistente);

            // Crea un DTO para el pedido actualizado y devuélvelo
            PedidoDTO pedidoActualizadoDTO = new PedidoDTO();
            pedidoActualizadoDTO.setId(pedidoExistente.getId());
            pedidoActualizadoDTO.setEstadoPedido(pedidoExistente.getEstadoPedido());
            pedidoActualizadoDTO.setFechaHora(pedidoExistente.getFechaHora().toString());
            // También configura otros campos según sea necesario

            return pedidoActualizadoDTO;
        } else {
            // Si el pedido con el ID proporcionado no existe, puedes manejarlo apropiadamente
            return null;
        }
    }

    public void eliminarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }
}
