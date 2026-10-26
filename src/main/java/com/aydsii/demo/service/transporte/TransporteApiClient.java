package com.aydsii.demo.service.transporte;

import com.aydsii.demo.enums.EstadoEnvio;
import com.aydsii.demo.enums.ServicioTransporte;

/**
 * Interfaz que define la estrategia común para la comunicación con los servicios de logística externos.
 * Aplica el patrón Strategy para desacoplar el sistema de los proveedores específicos de transporte.
 */
public interface TransporteApiClient {

    // Interfaz que define la estrategia común para la comunicación con los servicios de logística externos.
    ServicioTransporte getServicioTransporte();
    
    // Genera un número de seguimiento válido emitido por la empresa de transporte
    Long generarNumeroSeguimiento();

    // Consulta el estado del paquete en el webservice externo de la empresa de transporte.
    EstadoEnvio consultarEstadoSeguimiento(Long numeroSeguimiento);
}
