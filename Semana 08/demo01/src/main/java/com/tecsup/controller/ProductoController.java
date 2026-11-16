package com.tecsup.controller;

import com.tecsup.dto.ProductoDTO;
import com.tecsup.model.Producto;
import com.tecsup.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService service;
    public ProductoController(ProductoService service) { this.service=service; }
    @GetMapping public List<ProductoDTO> listar() { return service.listar().stream().map(ProductoController::toDto).toList(); }
    @GetMapping("/{id}") public ProductoDTO obtener(@PathVariable Long id) { return toDto(service.obtener(id)); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public ProductoDTO guardar(@Valid @RequestBody ProductoDTO dto) { return toDto(service.guardar(toModel(dto))); }
    @PutMapping("/{id}") public ProductoDTO actualizar(@PathVariable Long id, @Valid @RequestBody ProductoDTO dto) { return toDto(service.actualizar(id, toModel(dto))); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
    private static ProductoDTO toDto(Producto p) { return new ProductoDTO(p.getId(), p.getNombre(), p.getPrecio(), p.getStock()); }
    private static Producto toModel(ProductoDTO d) { return new Producto(null, d.getNombre(), d.getPrecio(), d.getStock()); }
}
