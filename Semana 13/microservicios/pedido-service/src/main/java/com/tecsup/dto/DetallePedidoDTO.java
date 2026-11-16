package com.tecsup.dto;

public class DetallePedidoDTO {

    private Long idProducto;
    private Integer cantidad;
    private Double precio;

    public DetallePedidoDTO() {
    }

    public DetallePedidoDTO(Long idProducto, Integer cantidad, Double precio) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}