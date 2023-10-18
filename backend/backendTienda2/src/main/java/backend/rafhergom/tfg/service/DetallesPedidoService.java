package backend.rafhergom.tfg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import backend.rafhergom.tfg.model.dtos.DetallesPedidoDTO;
import backend.rafhergom.tfg.model.entity.DetallesPedido;
import backend.rafhergom.tfg.model.entity.Pedido;
import backend.rafhergom.tfg.model.entity.Producto;
import backend.rafhergom.tfg.repository.DetallesPedidoRepository;
import backend.rafhergom.tfg.repository.PedidoRepository;
import backend.rafhergom.tfg.repository.ProductoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DetallesPedidoService {
    private final DetallesPedidoRepository detallesPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    @Autowired
    public DetallesPedidoService(
            DetallesPedidoRepository detallesPedidoRepository,
            PedidoRepository pedidoRepository,
            ProductoRepository productoRepository
    ) {
        this.detallesPedidoRepository = detallesPedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    public List<DetallesPedidoDTO> obtenerTodosLosDetallesPedido() {
        List<DetallesPedido> detallesPedido = detallesPedidoRepository.findAll();
        List<DetallesPedidoDTO> detallesPedidoDTOs = new ArrayList<>();

        for (DetallesPedido detallePedido : detallesPedido) {
            DetallesPedidoDTO detallesPedidoDTO = new DetallesPedidoDTO();
            // Copiar los atributos relevantes de DetallesPedido a DetallesPedidoDTO
            detallesPedidoDTO.setId(detallePedido.getId());
            detallesPedidoDTO.setPedidoId(detallePedido.getPedido().getId());
            detallesPedidoDTO.setProductoId(detallePedido.getProducto().getId());
            detallesPedidoDTO.setCantidad(detallePedido.getCantidad());
            detallesPedidoDTO.setPrecioUnitario(detallePedido.getPrecioUnitario());
            detallesPedidoDTOs.add(detallesPedidoDTO);
        }

        return detallesPedidoDTOs;
    }

    public DetallesPedidoDTO obtenerDetallesPedidoPorId(Long id) {
        Optional<DetallesPedido> detallesPedidoOptional = detallesPedidoRepository.findById(id);
        return detallesPedidoOptional.map(detallesPedido -> {
            DetallesPedidoDTO detallesPedidoDTO = new DetallesPedidoDTO();
            // Copiar los atributos relevantes de DetallesPedido a DetallesPedidoDTO
            detallesPedidoDTO.setId(detallesPedido.getId());
            detallesPedidoDTO.setPedidoId(detallesPedido.getPedido().getId());
            detallesPedidoDTO.setProductoId(detallesPedido.getProducto().getId());
            detallesPedidoDTO.setCantidad(detallesPedido.getCantidad());
            detallesPedidoDTO.setPrecioUnitario(detallesPedido.getPrecioUnitario());
            return detallesPedidoDTO;
        }).orElse(null);
    }

    public DetallesPedidoDTO crearDetallesPedido(DetallesPedidoDTO detallesPedidoDTO) {
        // Crea un nuevo objeto DetallesPedido a partir del DTO
        DetallesPedido nuevoDetallesPedido = new DetallesPedido();
        // Busca el pedido correspondiente y el producto correspondiente por sus IDs
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(detallesPedidoDTO.getPedidoId());
        Optional<Producto> productoOptional = productoRepository.findById(detallesPedidoDTO.getProductoId());

        if (pedidoOptional.isPresent() && productoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();
            Producto producto = productoOptional.get();
            
            nuevoDetallesPedido.setPedido(pedido);
            nuevoDetallesPedido.setProducto(producto);
            nuevoDetallesPedido.setCantidad(detallesPedidoDTO.getCantidad());
            nuevoDetallesPedido.setPrecioUnitario(detallesPedidoDTO.getPrecioUnitario());

            // Guarda el nuevo detalle de pedido en la base de datos utilizando el repository
            nuevoDetallesPedido = detallesPedidoRepository.save(nuevoDetallesPedido);

            // Crea un DTO para el nuevo detalle de pedido y devuélvelo
            DetallesPedidoDTO nuevoDetallesPedidoDTO = new DetallesPedidoDTO();
            nuevoDetallesPedidoDTO.setId(nuevoDetallesPedido.getId());
            nuevoDetallesPedidoDTO.setPedidoId(nuevoDetallesPedido.getPedido().getId());
            nuevoDetallesPedidoDTO.setProductoId(nuevoDetallesPedido.getProducto().getId());
            nuevoDetallesPedidoDTO.setCantidad(nuevoDetallesPedido.getCantidad());
            nuevoDetallesPedidoDTO.setPrecioUnitario(nuevoDetallesPedido.getPrecioUnitario());

            return nuevoDetallesPedidoDTO;
        } else {
            // Si no se encontraron el pedido o el producto correspondiente, puedes manejarlo apropiadamente
            return null;
        }
    }

    public DetallesPedidoDTO actualizarDetallesPedido(Long id, DetallesPedidoDTO detallesPedidoDTO) {
        // Verifica si el detalle de pedido con el ID proporcionado existe en la base de datos
        Optional<DetallesPedido> detallesPedidoOptional = detallesPedidoRepository.findById(id);
        if (detallesPedidoOptional.isPresent()) {
            // Obtén el detalle de pedido existente
            DetallesPedido detallesPedidoExistente = detallesPedidoOptional.get();

            // Actualiza los campos relevantes del detalle de pedido existente con los valores del DTO
            detallesPedidoExistente.setCantidad(detallesPedidoDTO.getCantidad());
            detallesPedidoExistente.setPrecioUnitario(detallesPedidoDTO.getPrecioUnitario());
            // También puedes necesitar actualizar otros campos

            // Guarda la actualización en la base de datos
            detallesPedidoExistente = detallesPedidoRepository.save(detallesPedidoExistente);

            // Crea un DTO para el detalle de pedido actualizado y devuélvelo
            DetallesPedidoDTO detallesPedidoActualizadoDTO = new DetallesPedidoDTO();
            detallesPedidoActualizadoDTO.setId(detallesPedidoExistente.getId());
            detallesPedidoActualizadoDTO.setPedidoId(detallesPedidoExistente.getPedido().getId());
            detallesPedidoActualizadoDTO.setProductoId(detallesPedidoExistente.getProducto().getId());
            detallesPedidoActualizadoDTO.setCantidad(detallesPedidoExistente.getCantidad());
            detallesPedidoActualizadoDTO.setPrecioUnitario(detallesPedidoExistente.getPrecioUnitario());
            // También configura otros campos según sea necesario

            return detallesPedidoActualizadoDTO;
        } else {
            // Si el detalle de pedido con el ID proporcionado no existe, puedes manejarlo apropiadamente
            return null;
        }
    }

    public void eliminarDetallesPedido(Long id) {
        detallesPedidoRepository.deleteById(id);
    }
}
