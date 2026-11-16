package com.tecsup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.dto.ProductoDTO;
import com.tecsup.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest @AutoConfigureMockMvc
class ProductoControllerTest {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired ProductoRepository repo;
    @BeforeEach void limpiar() { repo.deleteAll(); }
    private ProductoDTO dto(String nombre, double precio, int stock) { return new ProductoDTO(null,nombre,precio,stock); }

    @Test void testGuardarProducto() throws Exception {
        mockMvc.perform(post("/api/productos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto("Mouse",80,20))))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.nombre").value("Mouse"));
    }
    @Test void testListarProductos() throws Exception {
        mockMvc.perform(get("/api/productos")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray());
    }
    @Test void testValidacionNombreVacio() throws Exception {
        mockMvc.perform(post("/api/productos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto("",10,2))))
                .andExpect(status().isBadRequest());
    }
    @Test void testActualizarProducto() throws Exception {
        String creado=mockMvc.perform(post("/api/productos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto("Mouse",80,20))))
                .andReturn().getResponse().getContentAsString();
        long id=objectMapper.readTree(creado).get("id").asLong();
        mockMvc.perform(put("/api/productos/{id}",id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto("Mouse inalámbrico",100,12))))
                .andExpect(status().isOk()).andExpect(jsonPath("$.nombre").value("Mouse inalámbrico"));
    }
    @Test void testEliminarProducto() throws Exception {
        String creado=mockMvc.perform(post("/api/productos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto("Mouse",80,20))))
                .andReturn().getResponse().getContentAsString();
        long id=objectMapper.readTree(creado).get("id").asLong();
        mockMvc.perform(delete("/api/productos/{id}",id)).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/productos/{id}",id)).andExpect(status().isNotFound());
    }
}
