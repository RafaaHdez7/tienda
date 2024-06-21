package backend.rafhergom.tfg.utilities;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import backend.rafhergom.tfg.model.dtos.*;
import backend.rafhergom.tfg.model.entity.*;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // Configurar el mapeo de Producto a ProductoDTO
        ProductoToProductoDTO(modelMapper);
        PedidoToPedidoDTO(modelMapper);
        UsuarioToUsuarioDTO(modelMapper); 
        return modelMapper;
    }

    private void ProductoToProductoDTO(ModelMapper modelMapper) {
        TypeMap<Producto, ProductoDTO> typeMap = modelMapper.createTypeMap(Producto.class, ProductoDTO.class);

        TypeMap<Producto, CategoriaProductoDTO> categoriaProductoMap = modelMapper.createTypeMap(Producto.class, CategoriaProductoDTO.class);
        categoriaProductoMap.addMapping(src -> src.getCategoriaProducto().getId(), CategoriaProductoDTO::setId);
        TypeMap<Producto, NegocioDTO> negocioMap = modelMapper.createTypeMap(Producto.class, NegocioDTO.class);
        negocioMap.addMapping(src -> src.getNegocio().getId(), NegocioDTO::setId);
        typeMap.addMapping(src -> src.getCategoriaProducto(), ProductoDTO::setCategoriaProductoDTO);
        typeMap.addMapping(src -> src.getNegocio(), ProductoDTO::setNegocioDTO);
        
    }
    
    private void PedidoToPedidoDTO(ModelMapper modelMapper) {
        TypeMap<Pedido, PedidoDTO> typeMap = modelMapper.createTypeMap(Pedido.class, PedidoDTO.class);

        TypeMap<Pedido, UsuarioDTO> usuarioMap = modelMapper.createTypeMap(Pedido.class, UsuarioDTO.class);
        usuarioMap.addMapping(src -> src.getUsuario().getId(), UsuarioDTO::setId);
        TypeMap<Pedido, NegocioDTO> negocioMap = modelMapper.createTypeMap(Pedido.class, NegocioDTO.class);
        negocioMap.addMapping(src -> src.getNegocio().getId(), NegocioDTO::setId);
        typeMap.addMapping(src -> src.getUsuario(), PedidoDTO::setUsuarioDTO);
        typeMap.addMapping(src -> src.getNegocio(), PedidoDTO::setNegocioDTO);
        
        //TODO FALTARIA EL TEMA DE ESTADOSPEDIDOS
        
    }
    
    private void UsuarioToUsuarioDTO(ModelMapper modelMapper) {
        TypeMap<Usuario, UsuarioDTO> typeMap = modelMapper.createTypeMap(Usuario.class, UsuarioDTO.class);
        typeMap.addMapping(src -> src.getId(), UsuarioDTO::setId);
        typeMap.addMapping(src -> src.getNombre(), UsuarioDTO::setNombre);
        typeMap.addMapping(src -> src.getContrasena(), UsuarioDTO::setContrasena);
        typeMap.addMapping(src -> src.getEmail(), UsuarioDTO::setEmail);
        typeMap.addMapping(src -> src.getRol(), UsuarioDTO::setRol);
        typeMap.addMapping(src -> src.getActivo(), UsuarioDTO::setActivo);
    }
    
    private void CategoriaProductoToCategoriaProductoDTO(ModelMapper modelMapper) {
        TypeMap<CategoriaProducto, CategoriaProductoDTO> typeMap = modelMapper.createTypeMap(CategoriaProducto.class, CategoriaProductoDTO.class);
        typeMap.addMapping(src -> src.getId(), CategoriaProductoDTO::setId);
        typeMap.addMapping(src -> src.getNombreCategoria(), CategoriaProductoDTO::setNombreCategoria);
        typeMap.addMapping(src -> src.getDescripcion(), CategoriaProductoDTO::setDescripcion);
        typeMap.addMapping(src -> src.getImagenURL(), CategoriaProductoDTO::setImagenURL);

    }
    
    
    
//    private void LoginRequestDTOToUsuario(ModelMapper modelMapper) {
//        TypeMap<LoginRequestDTO, Usuario> typeMap = modelMapper.createTypeMap(LoginRequestDTO.class, Usuario.class);
//
//        TypeMap<Pedido, UsuarioDTO> usuarioMap = modelMapper.createTypeMap(Pedido.class, UsuarioDTO.class);
//        usuarioMap.addMapping(src -> src.getUsuario().getId(), UsuarioDTO::setId);
//        TypeMap<Pedido, NegocioDTO> negocioMap = modelMapper.createTypeMap(Pedido.class, NegocioDTO.class);
//        negocioMap.addMapping(src -> src.getNegocio().getId(), NegocioDTO::setId);
//        typeMap.addMapping(src -> src.getUsuario(), PedidoDTO::setUsuarioDTO);
//        typeMap.addMapping(src -> src.getNegocio(), PedidoDTO::setNegocioDTO);
//        
//        //TODO FALTARIA EL TEMA DE ESTADOSPEDIDOS
//        
//    }
//    private void ProductoDTOToProducto(ModelMapper modelMapper) {
//        TypeMap<ProductoDTO, Producto> typeMap = modelMapper.createTypeMap(ProductoDTO.class, Producto.class);
//        typeMap.addMapping(src -> src.getCategoriaProductoDTO().getId(), Producto::setCategoriaProducto);
//        typeMap.addMapping(src -> src.getNegocioDTO().getId(), Producto::setNegocio);
//
//    }
    
    
}
