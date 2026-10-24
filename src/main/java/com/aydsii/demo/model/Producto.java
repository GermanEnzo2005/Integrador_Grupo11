package com.aydsii.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    private String nombre;
    private String descripcion;
    private Float precio;
    private Integer stockActual;
    private Integer stockMinimo;
    private Float impactoAmbiental;
    private Integer puntos;

    // Responde si el stock actual alcanza para la cantidad pedida.
    public boolean alcanzaStock(int cantidad) {
        return stockActual >= cantidad;
    }

    public void descontarStock(int cantidad) {
        stockActual = stockActual - cantidad;
    }
}
