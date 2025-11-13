package com.example.EcomerceUribe.modelos.DTOS;

import java.time.LocalDate;
import java.util.List;

public class PedidoDTO {

    private Integer id;
    private Integer montoTotal;
    private LocalDate fechaCreacion;
    private LocalDate fechaEntrega;
    private Integer costoEnvio;
    private Integer clienteId;
    private List<ProductoDTO> productos;

    public PedidoDTO() {
    }

    public PedidoDTO(Integer id, Integer montoTotal, LocalDate fechaCreacion, LocalDate fechaEntrega,
                      Integer costoEnvio, Integer clienteId, List<ProductoDTO> productos) {
        this.id = id;
        this.montoTotal = montoTotal;
        this.fechaCreacion = fechaCreacion;
        this.fechaEntrega = fechaEntrega;
        this.costoEnvio = costoEnvio;
        this.clienteId = clienteId;
        this.productos = productos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Integer montoTotal) {
        this.montoTotal = montoTotal;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Integer getCostoEnvio() {
        return costoEnvio;
    }

    public void setCostoEnvio(Integer costoEnvio) {
        this.costoEnvio = costoEnvio;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public List<ProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoDTO> productos) {
        this.productos = productos;
    }
}
