package backend.rafhergom.tfg.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import backend.rafhergom.tfg.model.dtos.NegocioDTO;
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
    private final ModelMapper modelMapper;

    

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository,
    		@Qualifier("modelMapper") ModelMapper modelMapper) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    public List<PedidoDTO> obtenerTodosLosPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<PedidoDTO> pedidoDTOs = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            PedidoDTO pedidoDTO = modelMapper.map(pedido, PedidoDTO.class);
            pedidoDTOs.add(pedidoDTO);
        }

        return pedidoDTOs;
    }


    public PedidoDTO obtenerPedidoPorId(Long id) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        return pedidoOptional.map(pedido -> {
        	return modelMapper.map(pedido, PedidoDTO.class);
        }).orElse(null);
    }

    public PedidoDTO crearPedido(PedidoDTO pedidoDTO) {
        // Utiliza ModelMapper para mapear directamente de PedidoDTO a Pedido
        Pedido nuevoPedido = modelMapper.map(pedidoDTO, Pedido.class);

        // Busca el usuario correspondiente por su ID
        Usuario usuario = usuarioRepository.findById(pedidoDTO.getUsuarioDTO().getId()).orElse(null);
        nuevoPedido.setUsuario(usuario);

        // Guarda el nuevo pedido en la base de datos utilizando el repository
        nuevoPedido = pedidoRepository.save(nuevoPedido);

        // Utiliza ModelMapper para mapear de Pedido a PedidoDTO
        return modelMapper.map(nuevoPedido, PedidoDTO.class);
    }

    public PedidoDTO actualizarPedido(Long id, PedidoDTO pedidoDTO) {
        // Verifica si el pedido con el ID proporcionado existe en la base de datos
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        if (pedidoOptional.isPresent()) {
            // Obtén el pedido existente
            Pedido pedidoExistente = pedidoOptional.get();

            // Utiliza ModelMapper para mapear los valores del DTO al pedido existente
            modelMapper.map(pedidoDTO, pedidoExistente);

            // Guarda la actualización en la base de datos
            pedidoExistente = pedidoRepository.save(pedidoExistente);

            // Utiliza ModelMapper para mapear de Pedido a PedidoDTO
            return modelMapper.map(pedidoExistente, PedidoDTO.class);
        } else {
            // Manejo cuando el pedido con el ID proporcionado no existe
            return null;
        }
    }

    public void eliminarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }
}
