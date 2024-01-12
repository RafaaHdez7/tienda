package backend.rafhergom.tfg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import backend.rafhergom.tfg.model.dtos.NegocioDTO;
import backend.rafhergom.tfg.service.NegocioService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(tags = "NegocioController")
@RequestMapping("/api/negocio")
@Slf4j
public class NegocioController {

    private final NegocioService negocioService;

    @Autowired
    public NegocioController(NegocioService negocioService) {
        this.negocioService = negocioService;
    }

    @GetMapping
    public List<NegocioDTO> obtenerTodosLosNegocios() {
        return negocioService.obtenerTodosLosNegocios();
    }

    @GetMapping("/{id}")
    public NegocioDTO obtenerNegocioPorId(@PathVariable Long id) {
        return negocioService.obtenerNegocioPorId(id);
    }

    @PostMapping
    public NegocioDTO crearNegocio(@RequestBody NegocioDTO negocioDTO) {
        return negocioService.crearNegocio(negocioDTO);
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
