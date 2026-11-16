package com.tecsup.service;

import com.tecsup.exception.RecursoNoEncontradoException;
import com.tecsup.model.Producto;
import com.tecsup.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository repo;
    public ProductoService(ProductoRepository repo) { this.repo=repo; }
    public List<Producto> listar() { return repo.findAll(); }
    public Producto guardar(Producto producto) { return repo.save(producto); }
    public Producto obtener(Long id) { return repo.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con id: " + id)); }
    public Producto actualizar(Long id, Producto datos) {
        Producto producto = obtener(id);
        producto.setNombre(datos.getNombre()); producto.setPrecio(datos.getPrecio()); producto.setStock(datos.getStock());
        return repo.save(producto);
    }
    public void eliminar(Long id) { repo.deleteById(id); }
}
