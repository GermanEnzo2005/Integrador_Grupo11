package com.aydsii.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Nivel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNivel;

    private String nombreNivel;
    private Float descuentoEnvio;
    private Float descuentoPedido;
    private Integer puntosRequeridos;
}
