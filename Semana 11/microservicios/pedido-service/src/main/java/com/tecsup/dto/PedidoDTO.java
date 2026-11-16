package com.tecsup.dto;

import java.time.LocalDate;
import java.util.List;

public class PedidoDTO {

    private LocalDate fecha;
    private Long idCliente;
    private Double total;
    private List<DetallePedidoDTO> detalles;

    public PedidoDTO() {
    }

    public PedidoDTO(LocalDate fecha, Long idCliente, Double total, List<DetallePedidoDTO> detalles) {
        this.fecha = fecha;
        this.idCliente = idCliente;
        this.total = total;
        this.detalles = detalles;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<DetallePedidoDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedidoDTO> detalles) {
        this.detalles = detalles;
    }
}