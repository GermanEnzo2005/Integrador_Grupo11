package com.aydsii.demo.dto;

import com.aydsii.demo.enums.ServicioTransporte;
import com.aydsii.demo.enums.TipoEntrega;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 son los datos que el cliente manda al comprar su carrito
 * Es lo que Spring arma a partir del JSON del cuerpo del POST.
 */
@Data
public class ComprarDTO {

    @NotNull(message = "El id del carrito es obligatorio")
    private Long idCarrito;

    @NotNull(message = "El tipo de entrega es obligatorio")
    private TipoEntrega tipoEntrega;

    @NotNull(message = "El servicio de transporte es obligatorio")
    private ServicioTransporte servicioTransporte;

    @NotBlank(message = "La dirección de destino es obligatoria")
    private String dirDestino;

    @NotBlank(message = "La localidad es obligatoria")
    private String localidad;
}
