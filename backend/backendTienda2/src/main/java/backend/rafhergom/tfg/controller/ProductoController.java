package backend.rafhergom.tfg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import backend.rafhergom.tfg.model.dtos.ProductoDTO;
import backend.rafhergom.tfg.service.ProductoService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@CrossOrigin
@Api(tags = "ProductoController")
@RequestMapping("/api/productos")
@Slf4j
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<ProductoDTO> obtenerTodosLosProductos() {
        return productoService.obtenerTodosLosProductos();
    }

    @GetMapping("/{id}")
    public ProductoDTO obtenerProductoPorId(@PathVariable Long id) {
        return productoService.obtenerProductoPorId(id);
    }

    @PostMapping
    public ProductoDTO crearProducto(@RequestBody ProductoDTO productoDTO) {
        return productoService.crearProducto(productoDTO);
    }

    @PutMapping("/{id}")
    public ProductoDTO actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        return productoService.actualizarProducto(id, productoDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }
}
