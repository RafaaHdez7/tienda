package backend.rafhergom.tfg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import backend.rafhergom.tfg.model.dtos.CategoriaNegocioDTO;
import backend.rafhergom.tfg.service.CategoriaNegocioService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@Api(tags = "CategoriaNegocioController")
@RequestMapping("/api/categoria-negocios")
@Slf4j
public class CategoriaNegocioController {

    private final CategoriaNegocioService categoriaNegocioService;

    @Autowired
    public CategoriaNegocioController(CategoriaNegocioService categoriaNegocioService) {
        this.categoriaNegocioService = categoriaNegocioService;
    }

    @GetMapping
    public List<CategoriaNegocioDTO> obtenerTodasLasCategoriaNegocios() {
        return categoriaNegocioService.obtenerTodasLasCategoriaNegocios();
    }

    @GetMapping("/{id}")
    public CategoriaNegocioDTO obtenerCategoriaNegocioPorId(@PathVariable Long id) {
        return categoriaNegocioService.obtenerCategoriaNegocioPorId(id);
    }

    @PostMapping
    public CategoriaNegocioDTO crearCategoriaNegocio(@RequestBody CategoriaNegocioDTO categoriaNegocioDTO) {
        return categoriaNegocioService.crearCategoriaNegocio(categoriaNegocioDTO);
    }

    @PutMapping("/{id}")
    public CategoriaNegocioDTO actualizarCategoriaNegocio(@PathVariable Long id, @RequestBody CategoriaNegocioDTO categoriaNegocioDTO) {
        return categoriaNegocioService.actualizarCategoriaNegocio(id, categoriaNegocioDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminarCategoriaNegocio(@PathVariable Long id) {
        categoriaNegocioService.eliminarCategoriaNegocio(id);
    }
}
