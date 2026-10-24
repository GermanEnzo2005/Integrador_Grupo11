package com.aydsii.demo.dto;

import com.aydsii.demo.enums.EstadoPedido;
import com.aydsii.demo.model.Pedido;
import lombok.Data;

import java.time.LocalDate;

//los datos del pedido que se le muestran al usuario como repsuesta luego de comprar su carrito
@Data
public class PedidoDTO {

    private Long idPedido;
    private LocalDate fechaPedido;
    private Integer cantidadProductos;
    private Float subtotal;
    private Boolean descuentoMayorista;
    private Float descuentoNivel;
    private Float descuentoEnvioPorNivel;
    private Float costoEnvio;
    private Float total;
    private Float puntosGanados;
    private Float impactoAmbientalTotal;
    private EstadoPedido estadoPedido;

    public PedidoDTO(Pedido pedido) {
        this.idPedido = pedido.getIdPedido();
        this.fechaPedido = pedido.getFechaPedido();
        this.cantidadProductos = pedido.getCantidadProductos();
        this.subtotal = pedido.getSubtotal();
        this.descuentoMayorista = pedido.getDescuentoMayorista();
        this.descuentoNivel = pedido.getDescuentoNivel();
        this.descuentoEnvioPorNivel = pedido.getDescuentoEnvioPorNivel();
        this.costoEnvio = pedido.getCostoEnvio();
        this.total = pedido.getTotal();
        this.puntosGanados = pedido.getPuntosGanados();
        this.impactoAmbientalTotal = pedido.getImpactoAmbientalTotal();
        this.estadoPedido = pedido.getEstadoPedido();
    }
}