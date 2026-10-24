package com.aydsii.demo.model;

import com.aydsii.demo.enums.ServicioTransporte;
import com.aydsii.demo.enums.TipoEntrega;
import com.aydsii.demo.enums.EstadoPedido;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    private LocalDate fechaPedido;
    private Integer cantidadProductos;
    private Float subtotal;
    private Boolean descuentoMayorista;
    private Float descuentoNivel;
    private Float costoEnvio;
    private Float total;
    private Float puntosGanados;
    private Float descuentoEnvioPorNivel;
    private Float impactoAmbientalTotal;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estadoPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<DetallePedido> detalles = new ArrayList<>();


    public Pedido(Cliente cliente, List<DetallePedido> detalles, int cantidadProductos, float subtotal, float impactoAmbientalTotal, float puntosGanados, float descuentoEnvioPorNivel, float descuentoNivel) {
        this.cliente = cliente;
        this.detalles = detalles;
        this.cantidadProductos = cantidadProductos;
        this.subtotal = subtotal;
        this.impactoAmbientalTotal = impactoAmbientalTotal;
        this.puntosGanados = puntosGanados;
        this.descuentoEnvioPorNivel = descuentoEnvioPorNivel;
        this.descuentoNivel = descuentoNivel;
        this.fechaPedido = LocalDate.now();

        // cada detalle ahora sabe a que pedido pertenece lo que les falto asignar antes
        for (DetallePedido detalle : detalles) {
            detalle.setPedido(this);
        }
    }

    public void calcularCostoEnvio(double distanciaKm, TipoEntrega tipoEntrega, ServicioTransporte servicioTransporte) {
        float precioPorKm;
        if (servicioTransporte == ServicioTransporte.ANDREANI) {
            precioPorKm = 18f;
        } else {
            precioPorKm = 14f;
        }

        float costo = (float) distanciaKm * precioPorKm;

        if (tipoEntrega == TipoEntrega.DOMICILIO) {
            costo = costo * 1.20f;
        }

        this.costoEnvio = costo;
    }

    public boolean esMayorista() {
        return cantidadProductos >= 30;
    }

    public void aplicarDescuentoMayorista() {
        total = total - (total *  0.15f);
    }

    public void aplicarDescuentoNivel() {
        total = total - (total * descuentoNivel);
    }

    public void aplicarDescuentoEnvio() {
        costoEnvio = costoEnvio - (costoEnvio * descuentoEnvioPorNivel);
    }

    public void calcularTotal() {
        total = subtotal;

        descuentoMayorista = esMayorista();
        if (descuentoMayorista) {
            aplicarDescuentoMayorista();
        }

        aplicarDescuentoNivel();

        aplicarDescuentoEnvio();

        total = total + costoEnvio;
    }

    public void cambiarEstado(EstadoPedido nuevoEstado) {
        this.estadoPedido = nuevoEstado;
    }
}