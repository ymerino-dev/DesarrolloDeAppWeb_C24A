package com.tecsup.controller;

import com.tecsup.dto.CategoriaDTO;
import com.tecsup.model.Categoria;
import com.tecsup.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    private final CategoriaService service;
    public CategoriaController(CategoriaService service) { this.service=service; }
    @GetMapping public List<CategoriaDTO> listar() { return service.listar().stream().map(CategoriaController::toDto).toList(); }
    @GetMapping("/{id}") public CategoriaDTO obtener(@PathVariable Long id) { return toDto(service.obtener(id)); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public CategoriaDTO guardar(@Valid @RequestBody CategoriaDTO dto) { return toDto(service.guardar(toModel(dto))); }
    @PutMapping("/{id}") public CategoriaDTO actualizar(@PathVariable Long id, @Valid @RequestBody CategoriaDTO dto) { return toDto(service.actualizar(id, toModel(dto))); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
    private static CategoriaDTO toDto(Categoria c) { return new CategoriaDTO(c.getId(), c.getNombre(), c.getDescripcion()); }
    private static Categoria toModel(CategoriaDTO d) { return new Categoria(null, d.getNombre(), d.getDescripcion()); }
}
