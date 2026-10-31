package com.tecsup.service;

import com.tecsup.exception.RecursoNoEncontradoException;
import com.tecsup.model.Categoria;
import com.tecsup.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository repo;
    public CategoriaService(CategoriaRepository repo) { this.repo=repo; }
    public List<Categoria> listar() { return repo.findAll(); }
    public Categoria guardar(Categoria categoria) { return repo.save(categoria); }
    public Categoria obtener(Long id) { return repo.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con id: " + id)); }
    public Categoria actualizar(Long id, Categoria datos) {
        Categoria categoria=obtener(id); categoria.setNombre(datos.getNombre()); categoria.setDescripcion(datos.getDescripcion()); return repo.save(categoria);
    }
    public void eliminar(Long id) { repo.deleteById(id); }
}
