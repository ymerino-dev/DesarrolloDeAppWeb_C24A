package com.tecsup.service;

import com.tecsup.exception.RecursoNoEncontradoException;
import com.tecsup.model.Categoria;
import com.tecsup.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoriaServiceTest {
    @Mock private CategoriaRepository repo;
    @InjectMocks private CategoriaService service;
    @BeforeEach void setup() { MockitoAnnotations.openMocks(this); }
    @Test void listarCategorias() { when(repo.findAll()).thenReturn(List.of(new Categoria(1L,"Accesorios","Periféricos"))); assertEquals("Accesorios", service.listar().get(0).getNombre()); }
    @Test void guardarCategoria() { Categoria c=new Categoria(null,"Audio","Audífonos"); when(repo.save(c)).thenReturn(new Categoria(1L,"Audio","Audífonos")); assertEquals(1L, service.guardar(c).getId()); }
    @Test void obtenerCategoria() { when(repo.findById(1L)).thenReturn(Optional.of(new Categoria(1L,"Audio","Audífonos"))); assertEquals("Audio",service.obtener(1L).getNombre()); }
    @Test void obtenerCategoriaInexistente() { when(repo.findById(8L)).thenReturn(Optional.empty()); assertThrows(RecursoNoEncontradoException.class,()->service.obtener(8L)); }
    @Test void actualizarCategoria() { Categoria c=new Categoria(1L,"Audio","Vieja"); when(repo.findById(1L)).thenReturn(Optional.of(c)); when(repo.save(c)).thenReturn(c); Categoria r=service.actualizar(1L,new Categoria(null,"Sonido","Nueva")); assertEquals("Sonido",r.getNombre()); assertEquals("Nueva",r.getDescripcion()); }
    @Test void eliminarCategoria() { service.eliminar(1L); verify(repo).deleteById(1L); }
}
