package com.aydsii.demo.service.transporte;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.aydsii.demo.enums.EstadoEnvio;
import com.aydsii.demo.enums.ServicioTransporte;
import com.aydsii.demo.exception.TrackingException;

/**
 * Cliente de integración específico para el webservice de Correo Argentino.
 * Implementa la estrategia de consulta simulando validaciones de código y fallos de red.
 */
@Service
public class CorreoArgentinoApiClient implements TransporteApiClient {

    // Retorna el identificador del servicio Correo Argentino
    @Override
    public ServicioTransporte getServicioTransporte() {
        return ServicioTransporte.CORREO_ARGENTINO;
    }

    @Override
    public Long generarNumeroSeguimiento() {
        // Simulación: Correo Argentino genera códigos en el rango 100.000.000 - 199.999.999
        return ThreadLocalRandom.current().nextLong(1000000000L, 2000000000L);
    }

    // Simula la petición a la API de Correo Argentino para obtener el estado del paquete
    @Override
    public EstadoEnvio consultarEstadoSeguimiento(Long numeroSeguimiento) {
        // Simulación de validación (FA-01: Número de seguimiento inválido)
        if (numeroSeguimiento == null || numeroSeguimiento <= 0) {
            throw new TrackingException("El número de seguimiento proporcionado es inexistente en Correo Argentino.");
        }

        // Simulación de error de red (FE-01: Error de comunicación)
        // código de prueba de error: 99999L
        if (numeroSeguimiento == 99999L) {
            throw new TrackingException("No se pudo conectar con los servidores de Correo Argentino.");
        }

        // Retorno de estado actualizado desde el webservice de Correo Argentino
        return EstadoEnvio.EN_TRANSITO;
    }
}
