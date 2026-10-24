package com.aydsii.demo.model;

import com.aydsii.demo.enums.EstadoEnvio;
import com.aydsii.demo.enums.ServicioTransporte;
import com.aydsii.demo.enums.TipoEntrega;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEnvio;

    private String dirDestino;
    private Long numeroSeguimiento;
    private LocalDate fechaDespacho;
    private LocalDate fechaEstimadaLlegada;
    private LocalDate fechaEntregado;

    @Enumerated(EnumType.STRING)
    private EstadoEnvio estadoEnvio;

    @Enumerated(EnumType.STRING)
    private TipoEntrega tipoEntrega;

    @Enumerated(EnumType.STRING)
    private ServicioTransporte servicioTransporte;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_localidad")
    private Localidad localidad;

    public Envio(Pedido pedido, Localidad localidad, TipoEntrega tipoEntrega, String dirDestino, ServicioTransporte servicioTransporte) {
        this.pedido = pedido;
        this.localidad = localidad;
        this.tipoEntrega = tipoEntrega;
        this.dirDestino = dirDestino;
        this.servicioTransporte = servicioTransporte;
    }
}