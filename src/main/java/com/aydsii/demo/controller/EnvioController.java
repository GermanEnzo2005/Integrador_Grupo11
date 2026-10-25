package com.aydsii.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aydsii.demo.dto.ApiResponse;
import com.aydsii.demo.dto.SeguimientoEnvioDTO;
import com.aydsii.demo.service.EnvioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/envio")
@Tag(name = "Envío", description = "Endpoints para el seguimiento del estado de los envíos")
public class EnvioController {

    private final EnvioService envioService;

    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    @Operation(
        summary = "Consultar seguimiento de envío",
        description = "Permite a un cliente autenticado consultar el estado actualizado de su pedido."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Seguimiento de envío recuperado exitosamente.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class)
            )
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Petición inválida o el envío no posee número de seguimiento asignado (RN-02).",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Envío no encontrado con el ID proporcionado.",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "502",
            description = "Error de comunicación con la API externa de la empresa de transporte o número de seguimiento inexistente (FA-01 / FE-01).",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        )
    })
    @GetMapping("/{idEnvio}/seguimiento")
    public ResponseEntity<ApiResponse<SeguimientoEnvioDTO>> consultarSeguimiento(
            @Parameter(description = "Identificador único del envío a consultar", example = "1", required = true)
            @PathVariable Long idEnvio
            ) { 

        SeguimientoEnvioDTO seguimiento = envioService.consultarSeguimiento(idEnvio);

        return ResponseEntity.ok(ApiResponse.success(
                200, 
                "Seguimiento recuperado exitosamente", 
                seguimiento
        ));
    }
}
