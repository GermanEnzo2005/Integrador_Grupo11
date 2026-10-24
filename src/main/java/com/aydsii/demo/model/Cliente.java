package com.aydsii.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    private String dni;
    private String nombre;
    private String apellido;
    private String domicilio;
    private String telefono;
    private String mail;
    private Integer puntos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nivel")
    private Nivel nivel;


    public void sumarPuntos(int puntosNuevos) {
        puntos = puntos + puntosNuevos;
    }
}
