package backend.rafhergom.tfg.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import backend.rafhergom.tfg.model.dtos.CategoriaNegocioDTO;
import backend.rafhergom.tfg.model.dtos.DetallesPedidoDTO;
import backend.rafhergom.tfg.model.entity.CategoriaNegocio;
import backend.rafhergom.tfg.model.entity.DetallesPedido;
import backend.rafhergom.tfg.model.entity.Pedido;
import backend.rafhergom.tfg.model.entity.Producto;
import backend.rafhergom.tfg.repository.DetallesPedidoRepository;
import backend.rafhergom.tfg.repository.PedidoRepository;
import backend.rafhergom.tfg.repository.ProductoRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DetallesPedidoService {
    private final DetallesPedidoRepository detallesPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    private final ModelMapper modelMapper;
    @Autowired
    public DetallesPedidoService(
            DetallesPedidoRepository detallesPedidoRepository,
            PedidoRepository pedidoRepository,
            ProductoRepository productoRepository,
            @Qualifier("modelMapper")  ModelMapper modelMapper) {
    	
        this.detallesPedidoRepository = detallesPedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
        this.modelMapper = modelMapper;
    }

    public List<DetallesPedidoDTO> obtenerTodosLosDetallesPedido() {
        List<DetallesPedido> detallesPedido = detallesPedidoRepository.findAll();
        List<DetallesPedidoDTO> detallesPedidoDTOs = new ArrayList<>();

        for (DetallesPedido detallePedido : detallesPedido) {
            DetallesPedidoDTO detallesPedidoDTO =  modelMapper.map(detallePedido, DetallesPedidoDTO.class);
            detallesPedidoDTOs.add(detallesPedidoDTO);
        }
        return detallesPedidoDTOs;
    }

    public DetallesPedidoDTO obtenerDetallesPedidoPorId(Long id) {
    	DetallesPedidoDTO detallePedidoDTO =  detallesPedidoRepository.findById(id).map(detallesPedido -> {
           return modelMapper.map(detallesPedido, DetallesPedidoDTO.class);
        }).orElse(null);
    	detallePedidoDTO.getPedido().getUsuarioDTO().setContrasena(null);
    	detallePedidoDTO.getProducto().setImagenURL(null);
    	detallePedidoDTO.getPedido().getNegocioDTO().setImagenURL(null);
    	return detallePedidoDTO;
    }
    
//    public List<DetallesPedidoDTO> obtenerDetallesPedidoPorIdPedido(Long idPedido) {
//    	List<DetallesPedido> detallePedidoList =  detallesPedidoRepository.getByPedidoId(idPedido.toString());
//    	List<DetallesPedidoDTO> detallePedidoDTOList = new List<DetallesPedidoDTO>();
//    	for(DetallesPedido dp : detallePedidoList ) {
//    		DetallesPedidoDTO detallePedidoDTO =  detallesPedidoRepository.findById(id).map(detallesPedido -> {
//    	           return modelMapper.map(detallesPedido, DetallesPedidoDTO.class);
//    	        }).orElse(null);
//    	    	detallePedidoDTO.getPedido().getUsuarioDTO().setContrasena(null);
//    	    	detallePedidoDTO.getProducto().setImagenURL(null);
//    	    	detallePedidoDTO.getPedido().getNegocioDTO().setImagenURL(null);
//    	    	
//    	}
//    	
//    	return detallePedidoList;
//    }

    public DetallesPedidoDTO crearDetallesPedido(DetallesPedidoDTO detallesPedidoDTO) {
        // Utiliza ModelMapper para mapear de DetallesPedidoDTO a DetallesPedido
        //DetallesPedido nuevoDetallesPedido = modelMapper.map(detallesPedidoDTO, DetallesPedido.class);
    	DetallesPedido nuevoDetallesPedido = new DetallesPedido();
        


        // Busca el pedido correspondiente y el producto correspondiente por sus IDs
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(detallesPedidoDTO.getPedido().getId());
        Optional<Producto> productoOptional = productoRepository.findById(detallesPedidoDTO.getProducto().getId());

        if (pedidoOptional.isPresent() && productoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();
            Producto producto = productoOptional.get();
            nuevoDetallesPedido.setCantidad(detallesPedidoDTO.getCantidad());
            nuevoDetallesPedido.setFechaCreacion(new Date());
            nuevoDetallesPedido.setFechaModificacion(new Date());

            nuevoDetallesPedido.setPrecioUnitario(detallesPedidoDTO.getPrecioUnitario());
            nuevoDetallesPedido.setPedido(pedido);
            nuevoDetallesPedido.setProducto(producto);

            // Guarda el nuevo detalle de pedido en la base de datos utilizando el repository
            nuevoDetallesPedido = detallesPedidoRepository.save(nuevoDetallesPedido);

            // Utiliza ModelMapper para mapear de DetallesPedido a DetallesPedidoDTO
            return modelMapper.map(nuevoDetallesPedido, DetallesPedidoDTO.class);
        } else {
            // Manejo cuando el pedido o el producto no existen
            return null;
        }
    }

    public DetallesPedidoDTO actualizarDetallesPedido(Long id, DetallesPedidoDTO detallesPedidoDTO) {
        // Verifica si el detalle de pedido con el ID proporcionado existe en la base de datos
        Optional<DetallesPedido> detallesPedidoOptional = detallesPedidoRepository.findById(id);
        if (detallesPedidoOptional.isPresent()) {
            // Obtén el detalle de pedido existente
            DetallesPedido detallesPedidoExistente = detallesPedidoOptional.get();

            // Utiliza ModelMapper para mapear los valores del DTO al detalle de pedido existente
            modelMapper.map(detallesPedidoDTO, detallesPedidoExistente);

            // Guarda la actualización en la base de datos
            detallesPedidoExistente = detallesPedidoRepository.save(detallesPedidoExistente);

            // Utiliza ModelMapper para mapear de DetallesPedido a DetallesPedidoDTO
            return modelMapper.map(detallesPedidoExistente, DetallesPedidoDTO.class);
        } else {
            // Manejo cuando el detalle de pedido con el ID proporcionado no existe
            return null;
        }
    }

    public void eliminarDetallesPedido(Long id) {
        detallesPedidoRepository.deleteById(id);
    }
}
