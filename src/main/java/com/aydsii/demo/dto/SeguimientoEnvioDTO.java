package com.aydsii.demo.dto;

import java.time.LocalDate;

import com.aydsii.demo.enums.EstadoEnvio;
import com.aydsii.demo.enums.ServicioTransporte;
import com.aydsii.demo.enums.TipoEntrega;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO que contiene la información detallada del seguimiento del envío")
public class SeguimientoEnvioDTO {


    @Schema(description = "Empresa encargada del transporte", example = "ANDREANI")
    private ServicioTransporte servicioTransporte;

    @Schema(description = "Modalidad de entrega seleccionada", example = "DOMICILIO")
    private TipoEntrega tipoEntrega;

    @Schema(description = "Estado actual del proceso de envío", example = "EN_REPARTO")
    private EstadoEnvio estadoEnvio;

    @Schema(description = "Dirección física o punto de entrega de destino", example = "Calle 20 Nro 456")
    private String dirDestino;

    @Schema(description = "Fecha en la que el paquete fue despachado", example = "2026-10-25")
    private LocalDate fechaDespacho;

    @Schema(description = "Fecha estimada de llegada del envío", example = "2026-10-29")
    private LocalDate fechaEstimadaLlegada;

    @Schema(description = "Fecha exacta en la que se confirmó la entrega", example = "2026-10-28", nullable = true)
    private LocalDate fechaEntregado;

    @Schema(description = "Mensaje o estado descriptivo retornado por la API externa del transporte", example = "Estado verificado con ANDREANI")
    private String detalleEstadoExtranjero; // Detalle proveniente de la API externa

    
    public SeguimientoEnvioDTO() {
    }

    public SeguimientoEnvioDTO(Long idEnvio, Long numeroSeguimiento, ServicioTransporte servicioTransporte,
                               TipoEntrega tipoEntrega, EstadoEnvio estadoEnvio, String dirDestino,
                               String ciudadDestino, LocalDate fechaDespacho, LocalDate fechaEstimadaLlegada,
                               LocalDate fechaEntregado, String detalleEstadoExtranjero) {
        this.servicioTransporte = servicioTransporte;
        this.tipoEntrega = tipoEntrega;
        this.estadoEnvio = estadoEnvio;
        this.dirDestino = dirDestino;
        this.fechaDespacho = fechaDespacho;
        this.fechaEstimadaLlegada = fechaEstimadaLlegada;
        this.fechaEntregado = fechaEntregado;
        this.detalleEstadoExtranjero = detalleEstadoExtranjero;
    }

    // --- Getters y Setters ---

    public ServicioTransporte getServicioTransporte() { return servicioTransporte; }
    public void setServicioTransporte(ServicioTransporte servicioTransporte) { this.servicioTransporte = servicioTransporte; }
    
    public TipoEntrega getTipoEntrega() { return tipoEntrega; }
    public void setTipoEntrega(TipoEntrega tipoEntrega) { this.tipoEntrega = tipoEntrega; }

    public EstadoEnvio getEstadoEnvio() { return estadoEnvio; }
    public void setEstadoEnvio(EstadoEnvio estadoEnvio) { this.estadoEnvio = estadoEnvio; }

    public String getDirDestino() { return dirDestino; }
    public void setDirDestino(String dirDestino) { this.dirDestino = dirDestino; }

    public LocalDate getFechaDespacho() { return fechaDespacho; }
    public void setFechaDespacho(LocalDate fechaDespacho) { this.fechaDespacho = fechaDespacho; }

    public LocalDate getFechaEstimadaLlegada() { return fechaEstimadaLlegada; }
    public void setFechaEstimadaLlegada(LocalDate fechaEstimadaLlegada) { this.fechaEstimadaLlegada = fechaEstimadaLlegada; }

    public LocalDate getFechaEntregado() { return fechaEntregado; }
    public void setFechaEntregado(LocalDate fechaEntregado) { this.fechaEntregado = fechaEntregado; }

    public String getDetalleEstadoExtranjero() { return detalleEstadoExtranjero; }
    public void setDetalleEstadoExtranjero(String detalleEstadoExtranjero) { this.detalleEstadoExtranjero = detalleEstadoExtranjero; }
}
