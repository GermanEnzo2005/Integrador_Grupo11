package com.aydsii.demo.dto;

import com.aydsii.demo.enums.ServicioTransporte;
import com.aydsii.demo.enums.TipoEntrega;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 son los datos que el cliente manda al comprar su carrito
 * Es lo que Spring arma a partir del JSON del cuerpo del POST.
 */
@Data
public class ComprarDTO {

    @Schema (description = "Id asociado al carrito", example = "1")
    @NotNull(message = "El id del carrito es obligatorio")
    private Long idCarrito;

    @Schema (description = "Tipo asociado a la entrega (DOMICILIO, PUNTO DE ENTREGA)", example = "DOMICILIO")
    @NotNull(message = "El tipo de entrega es obligatorio")
    private TipoEntrega tipoEntrega;

    @Schema (description = "Empresa encargada del transporte", example = "ANDREANI")
    @NotNull(message = "El servicio de transporte es obligatorio")
    private ServicioTransporte servicioTransporte;

    @Schema (description = "Direccion de destino", example = "Calle 20 N 500")
    @NotBlank(message = "La dirección de destino es obligatoria")
    private String dirDestino;

    @Schema (description = "Ciudad de destino", example = "Realico")
    @NotBlank(message = "La localidad es obligatoria")
    private String localidad;
}
