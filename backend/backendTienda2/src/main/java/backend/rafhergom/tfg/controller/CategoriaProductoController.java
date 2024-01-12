package backend.rafhergom.tfg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import backend.rafhergom.tfg.model.dtos.CategoriaProductoDTO;
import backend.rafhergom.tfg.service.CategoriaProductoService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@Api(tags = "CategoriaProductoController")
@RequestMapping("/api/categoria-productos")
@Slf4j
public class CategoriaProductoController {

    private final CategoriaProductoService categoriaProductoService;

    @Autowired
    public CategoriaProductoController(CategoriaProductoService categoriaProductoService) {
        this.categoriaProductoService = categoriaProductoService;
    }

    @GetMapping
    public List<CategoriaProductoDTO> obtenerTodasLascategoriaProductos() {
        return categoriaProductoService.obtenerTodasLasCategoriaProductos();
    }

    @GetMapping("/{id}")
    public CategoriaProductoDTO obtenercategoriaProductoPorId(@PathVariable Long id) {
        return categoriaProductoService.obtenerCategoriaProductoPorId(id);
    }

    @PostMapping
    public CategoriaProductoDTO crearcategoriaProducto(@RequestBody CategoriaProductoDTO CategoriaProductoDTO) {
        return categoriaProductoService.crearCategoriaProducto(CategoriaProductoDTO);
    }

    @PutMapping("/{id}")
    public CategoriaProductoDTO actualizarcategoriaProducto(@PathVariable Long id, @RequestBody CategoriaProductoDTO CategoriaProductoDTO) {
        return categoriaProductoService.actualizarCategoriaProducto(id, CategoriaProductoDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminarcategoriaProducto(@PathVariable Long id) {
        categoriaProductoService.eliminarCategoriaProducto(id);
    }
}
