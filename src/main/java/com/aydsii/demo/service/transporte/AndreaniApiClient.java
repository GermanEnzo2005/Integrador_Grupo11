package com.aydsii.demo.service.transporte;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.aydsii.demo.enums.EstadoEnvio;
import com.aydsii.demo.enums.ServicioTransporte;
import com.aydsii.demo.exception.TrackingException;

/**
 * Cliente de integración específico para el webservice de Andreani.
 * Implementa la estrategia de consulta simulando validaciones de código y fallos de red.
 */
@Service
public class AndreaniApiClient implements TransporteApiClient {

    // Retorna el identificador del servicio Andreani
    @Override
    public ServicioTransporte getServicioTransporte() {
        return ServicioTransporte.ANDREANI;
    }

    @Override
    public Long generarNumeroSeguimiento() {
        // Simulación: Andreani genera códigos en el rango 800.000.000 - 899.999.999
        return ThreadLocalRandom.current().nextLong(800000000L, 900000000L);
    }

    // Simula la petición HTTP a la API de Andreani para obtener el estado del paquete
    @Override
    public EstadoEnvio consultarEstadoSeguimiento(Long numeroSeguimiento) {
        // Simulación de validación (FA-01: Número de seguimiento inválido)
        if (numeroSeguimiento == null || numeroSeguimiento <= 0) {
            throw new TrackingException("El número de seguimiento proporcionado no es válido para Andreani.");
        }

        // Simulación de error de red (FE-01: Error de comunicación) 
        // código de prueba de error: 99999L
        if (numeroSeguimiento == 99999L) {
            throw new TrackingException("Error de comunicación con el servicio de Andreani. Intente nuevamente más tarde.");
        }

        // Retorno de estado actualizado desde el webservice de Andreani
        return EstadoEnvio.EN_REPARTO;
    }
}