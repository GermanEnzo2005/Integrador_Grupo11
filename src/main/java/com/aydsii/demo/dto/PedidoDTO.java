package com.aydsii.demo.dto;

import com.aydsii.demo.enums.EstadoPedido;
import com.aydsii.demo.model.Pedido;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

//los datos del pedido que se le muestran al usuario como repsuesta luego de comprar su carrito
@Data
public class PedidoDTO {

  @Schema(description = "Identificador único del pedido", example = "1")
    private Long idPedido;

    @Schema(description = "Fecha en que se realizó el pedido", example = "2026-10-25")
    private LocalDate fechaPedido;

    @Schema(description = "Cantidad total de productos del pedido", example = "3")
    private Integer cantidadProductos;

    @Schema(description = "Suma de los precios de los productos antes de descuentos y envío", example = "15000.00")
    private Float subtotal;

    @Schema(description = "Indica si se aplicó descuento mayorista al pedido", example = "false")
    private Boolean descuentoMayorista;

    @Schema(description = "Descuento aplicado según el nivel del usuario", example = "500.00")
    private Float descuentoNivel;

    @Schema(description = "Descuento sobre el costo de envío según el nivel del usuario", example = "100.00")
    private Float descuentoEnvioPorNivel;

    @Schema(description = "Costo de envío del pedido", example = "800.00")
    private Float costoEnvio;

    @Schema(description = "Monto final a pagar, con descuentos y envío incluidos", example = "15200.00")
    private Float total;

    @Schema(description = "Puntos que obtiene el usuario con este pedido", example = "150.0")
    private Float puntosGanados;

    @Schema(description = "Impacto ambiental total del pedido", example = "12.5")
    private Float impactoAmbientalTotal;

    @Schema(description = "Estado actual del pedido", example = "CONFIRMADO")
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