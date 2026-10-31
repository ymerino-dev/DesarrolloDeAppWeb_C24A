package com.tecsup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.dto.CategoriaDTO;
import com.tecsup.repository.CategoriaRepository;
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
class CategoriaControllerTest {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired CategoriaRepository repo;
    @BeforeEach void limpiar() { repo.deleteAll(); }
    private CategoriaDTO dto(String nombre, String descripcion) { return new CategoriaDTO(null,nombre,descripcion); }
    @Test void crudCategoria() throws Exception {
        String body=objectMapper.writeValueAsString(dto("Accesorios","Periféricos"));
        String creado=mockMvc.perform(post("/api/categorias").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.nombre").value("Accesorios"))
                .andReturn().getResponse().getContentAsString();
        long id=objectMapper.readTree(creado).get("id").asLong();
        mockMvc.perform(get("/api/categorias")).andExpect(status().isOk()).andExpect(jsonPath("$[0].nombre").value("Accesorios"));
        mockMvc.perform(get("/api/categorias/{id}",id)).andExpect(status().isOk()).andExpect(jsonPath("$.descripcion").value("Periféricos"));
        mockMvc.perform(put("/api/categorias/{id}",id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto("Audio","Audífonos"))))
                .andExpect(status().isOk()).andExpect(jsonPath("$.nombre").value("Audio"));
        mockMvc.perform(delete("/api/categorias/{id}",id)).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/categorias/{id}",id)).andExpect(status().isNotFound());
    }
    @Test void validaNombreObligatorio() throws Exception {
        mockMvc.perform(post("/api/categorias").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto(" ","Descripción"))))
                .andExpect(status().isBadRequest());
    }
}
