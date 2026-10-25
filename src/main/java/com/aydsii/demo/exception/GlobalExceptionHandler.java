package com.aydsii.demo.exception;

import com.aydsii.demo.dto.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * Convierte las excepciones de los casos de uso (Comprar Carrito y Seguimiento de Envío)
 * en respuestas HTTP uniformes con el formato de ApiResponse.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Validación de DTOs con @Valid en Controllers (CU-01).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> manejarValidacion(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(ApiResponse.error(400, mensaje));
    }

    /**
     * Excepciones de reglas de negocio generales (CU-01 y CU-02).
     * Ej: Carrito vacío, permisos insuficientes (RN-01), etc.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> manejarNegocio(BusinessException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(ApiResponse.error(ex.getStatus().value(), ex.getMessage()));
    }

    /**
     * Excepciones del servicio de seguimiento de envíos (CU-02).
     * Maneja número de seguimiento inválido (FA-01) y fallos de comunicación con transporte (FE-01).
     */
    @ExceptionHandler(TrackingException.class)
    public ResponseEntity<ApiResponse<Void>> manejarTracking(TrackingException ex) {
        // Devuelve 502 BAD_GATEWAY indicando error al consultar la API del servicio externo
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(ApiResponse.error(HttpStatus.BAD_GATEWAY.value(), ex.getMessage()));
    }

    /**
     * Captura de errores no controlados para evitar HTTP 500 sin formato.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> manejarGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(500, "Ocurrió un error interno e inesperado en el servidor."));
    }
}
