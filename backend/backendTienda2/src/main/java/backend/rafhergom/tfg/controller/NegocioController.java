package backend.rafhergom.tfg.controller;

import java.util.List;

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
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(tags = "NegocioController")
@RequestMapping("/api/negocio")
@Slf4j
public class NegocioController {

	private final ModelMapper modelMapper;
    private final NegocioService negocioService;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public NegocioController(NegocioService negocioService, UsuarioRepository usuarioRepository,
    		@Qualifier("modelMapper")  ModelMapper modelMapper) {
        this.negocioService = negocioService;
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<NegocioDTO> obtenerTodosLosNegocios() {
        return negocioService.obtenerTodosLosNegocios();
    }

    @GetMapping("/{id}")
    public NegocioDTO obtenerNegocioPorId(@PathVariable Long id) {
        return negocioService.obtenerNegocioPorId(id);
    }
    
    @GetMapping("/usuario/{nombreUsuario}")
    public List<NegocioDTO> obtenerNegocioPorNombreUsuarioId(@PathVariable String nombreUsuario) {
    	Usuario user = usuarioRepository.findByNombre(nombreUsuario);
        return negocioService.obtenerNegocioPorUsuarioId(user.getId());
    }

    @PostMapping
    public NegocioDTO crearNegocio(@RequestBody NegocioDTO negocio) {
        return negocioService.crearNegocio(negocio);
    }

    @PutMapping("/{id}")
    public NegocioDTO actualizarNegocio(@PathVariable Long id, @RequestBody NegocioDTO negocioDTO) {
        return negocioService.actualizarNegocio(id, negocioDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminarNegocio(@PathVariable Long id) {
        negocioService.eliminarNegocio(id);
    }
}
