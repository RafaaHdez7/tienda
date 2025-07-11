package backend.rafhergom.tfg.controller;

import java.util.List;

import javax.annotation.security.PermitAll;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import backend.rafhergom.tfg.model.dtos.CrearNegocioDTO;
import backend.rafhergom.tfg.model.dtos.NegocioDTO;
import backend.rafhergom.tfg.model.dtos.UsuarioDTO;
import backend.rafhergom.tfg.model.entity.Usuario;
import backend.rafhergom.tfg.repository.UsuarioRepository;
import backend.rafhergom.tfg.service.NegocioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@Tag(name = "NegocioController", description = "Controlador para gestionar negocios")
@RequestMapping("/api/negocio")
@Slf4j
public class NegocioController {

    private final ModelMapper modelMapper;
    private final NegocioService negocioService;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public NegocioController(NegocioService negocioService, UsuarioRepository usuarioRepository,
                             @Qualifier("modelMapper") ModelMapper modelMapper) {
        this.negocioService = negocioService;
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Obtener todos los negocios", description = "Retorna una lista de todos los negocios disponibles.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de negocios obtenida correctamente")
    })
    @GetMapping
    @PermitAll
    public List<NegocioDTO> obtenerTodosLosNegocios() {
        return negocioService.obtenerTodosLosNegocios();
    }

    @Operation(summary = "Obtener negocio por ID", description = "Retorna el negocio asociado al ID especificado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Negocio encontrado"),
        @ApiResponse(responseCode = "404", description = "Negocio no encontrado")
    })
    @GetMapping("/{id}")
    public NegocioDTO obtenerNegocioPorId(@PathVariable Long id) {
        return negocioService.obtenerNegocioPorId(id);
    }
    
    @Operation(summary = "Obtener negocios por nombre de usuario", description = "Retorna los negocios asociados al nombre de usuario especificado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Negocios encontrados"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/usuario/{nombreUsuario}")
    public List<NegocioDTO> obtenerNegocioPorNombreUsuarioId(@PathVariable String nombreUsuario) {
        Usuario user = usuarioRepository.findByNombre(nombreUsuario);
        return negocioService.obtenerNegocioPorUsuarioId(user.getId());
    }

    @Operation(summary = "Crear un nuevo negocio", description = "Crea un nuevo negocio con la información proporcionada.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Negocio creado exitosamente")
    })
    @PostMapping
    public NegocioDTO crearNegocio(@RequestBody NegocioDTO negocio) {
        return negocioService.crearNegocio(negocio);
    }

    @Operation(summary = "Actualizar un negocio", description = "Actualiza el negocio existente con los datos proporcionados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Negocio actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Negocio no encontrado")
    })
    @PostMapping("/update/{id}")
    public NegocioDTO actualizarNegocio(@PathVariable Long id, @RequestBody NegocioDTO negocioDTO) {
        return negocioService.actualizarNegocio(id, negocioDTO);
    }

    @Operation(summary = "Eliminar un negocio", description = "Elimina el negocio especificado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Negocio eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Negocio no encontrado")
    })
    @DeleteMapping("/{id}")
    public void eliminarNegocio(@PathVariable Long id) {
        negocioService.eliminarNegocio(id);
    }
}
