package com.tecsup.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ConfiguracionController {

    @Value("${empresa.ambiente}")
    private String ambiente;

    @Value("${empresa.perfil}")
    private String perfil;

    @Value("${server.port}")
    private String puerto;

    @Value("${spring.datasource.url}")
    private String baseDatos;

    @GetMapping("/configuracion")
    public Map<String, String> obtenerConfiguracion() {
        Map<String, String> config = new HashMap<>();
        config.put("ambiente", ambiente);
        config.put("perfil", perfil);
        config.put("puerto", puerto);
        config.put("baseDatos", baseDatos);
        return config;
    }
}