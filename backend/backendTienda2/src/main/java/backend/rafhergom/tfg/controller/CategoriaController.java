package backend.rafhergom.tfg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import backend.rafhergom.tfg.model.dtos.CategoriaDTO;
import backend.rafhergom.tfg.service.CategoriaService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@Api(tags = "CategoriaController")
@RequestMapping("/api/categorias")
@Slf4j
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<CategoriaDTO> obtenerTodasLasCategorias() {
        return categoriaService.obtenerTodasLasCategorias();
    }

    @GetMapping("/{id}")
    public CategoriaDTO obtenerCategoriaPorId(@PathVariable Long id) {
        return categoriaService.obtenerCategoriaPorId(id);
    }

    @PostMapping
    public CategoriaDTO crearCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        return categoriaService.crearCategoria(categoriaDTO);
    }

    @PutMapping("/{id}")
    public CategoriaDTO actualizarCategoria(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) {
        return categoriaService.actualizarCategoria(id, categoriaDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
    }
}
