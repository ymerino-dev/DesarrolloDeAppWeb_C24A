package com.tecsup.dto;

import jakarta.validation.constraints.*;

public class ProductoDTO {
    private Long id;
    @NotBlank(message = "El nombre es obligatorio") @Size(max = 120)
    private String nombre;
    @NotNull(message = "El precio es obligatorio") @PositiveOrZero(message = "El precio debe ser mayor o igual a cero")
    private Double precio;
    @NotNull(message = "El stock es obligatorio") @PositiveOrZero(message = "El stock debe ser mayor o igual a cero")
    private Integer stock;
    public ProductoDTO() {}
    public ProductoDTO(Long id, String nombre, Double precio, Integer stock) { this.id=id; this.nombre=nombre; this.precio=precio; this.stock=stock; }
    public Long getId() { return id; } public void setId(Long id) { this.id=id; }
    public String getNombre() { return nombre; } public void setNombre(String nombre) { this.nombre=nombre; }
    public Double getPrecio() { return precio; } public void setPrecio(Double precio) { this.precio=precio; }
    public Integer getStock() { return stock; } public void setStock(Integer stock) { this.stock=stock; }
}
