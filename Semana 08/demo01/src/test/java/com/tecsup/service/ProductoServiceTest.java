package com.tecsup.service;

import com.tecsup.exception.RecursoNoEncontradoException;
import com.tecsup.model.Producto;
import com.tecsup.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {
    @Mock private ProductoRepository repo;
    @InjectMocks private ProductoService service;
    @BeforeEach void setup() { MockitoAnnotations.openMocks(this); }

    @Test void testListarProductos() {
        when(repo.findAll()).thenReturn(List.of(new Producto(1L, "Monitor", 500, 10)));
        List<Producto> resultado=service.listar();
        assertEquals(1, resultado.size()); assertEquals("Monitor", resultado.get(0).getNombre()); verify(repo).findAll();
    }
    @Test void testGuardarProducto() {
        Producto producto=new Producto(1L, "Laptop", 3000, 5);
        when(repo.save(producto)).thenReturn(new Producto(1L, "Laptop", 3000, 5));
        Producto resultado=service.guardar(producto);
        assertNotNull(resultado); assertEquals("Laptop", resultado.getNombre()); verify(repo).save(producto);
    }
    @Test void testObtenerProducto() {
        Producto producto=new Producto(1L, "Teclado", 100, 20); when(repo.findById(1L)).thenReturn(Optional.of(producto));
        assertEquals("Teclado", service.obtener(1L).getNombre()); verify(repo).findById(1L);
    }
    @Test void testObtenerProductoInexistente() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RecursoNoEncontradoException.class, () -> service.obtener(99L));
    }
    @Test void testActualizarProducto() {
        Producto existente=new Producto(1L, "Teclado", 100, 20);
        Producto cambios=new Producto(null, "Teclado mecánico", 150, 12);
        when(repo.findById(1L)).thenReturn(Optional.of(existente)); when(repo.save(existente)).thenReturn(existente);
        Producto resultado=service.actualizar(1L, cambios);
        assertEquals("Teclado mecánico", resultado.getNombre()); assertEquals(150, resultado.getPrecio()); assertEquals(12, resultado.getStock());
        verify(repo).save(existente);
    }
    @Test void testEliminarProducto() {
        service.eliminar(1L); verify(repo).deleteById(1L);
    }
}
