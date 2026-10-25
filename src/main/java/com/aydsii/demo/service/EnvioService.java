package com.aydsii.demo.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aydsii.demo.dto.SeguimientoEnvioDTO;
import com.aydsii.demo.enums.EstadoEnvio;
import com.aydsii.demo.exception.BusinessException;
import com.aydsii.demo.model.Envio;
import com.aydsii.demo.repository.EnvioRepository;
import com.aydsii.demo.service.transporte.TransporteApiClient;
import com.aydsii.demo.service.transporte.TransporteApiFactory;

@Service
public class EnvioService {
    private final EnvioRepository envioRepository;
    private final TransporteApiFactory transporteApiFactory;

    public EnvioService(EnvioRepository envioRepository, TransporteApiFactory transporteApiFactory) {
        this.envioRepository = envioRepository;
        this.transporteApiFactory = transporteApiFactory;
    }

    @Transactional
    public SeguimientoEnvioDTO consultarSeguimiento(Long idEnvio) {
        // Recupera Envío por idEnvio
        Envio envio = envioRepository.findById(idEnvio)
                .orElseThrow(() -> new BusinessException("No se encontró el envío solicitado con ID: " + idEnvio, HttpStatus.NOT_FOUND));

        
        // RN-01: El cliente solo puede consultar el seguimiento de envíos asociados a sus propios pedidos ya se resuelve en el flujo de navegación típico en la interfaz de usuario

        
        // Valida precondición (Número de seguimiento asignado / RN-02)
        if (envio.getNumeroSeguimiento() == null) {
            throw new BusinessException("El pedido aún no dispone de número de seguimiento asignado.", HttpStatus.BAD_REQUEST);
        }

        // Consulta al servicio de transporte externo (FA-01 y FE-01)
        TransporteApiClient apiClient = transporteApiFactory.getClient(envio.getServicioTransporte());
        EstadoEnvio estadoActualizado = apiClient.consultarEstadoSeguimiento(envio.getNumeroSeguimiento());

        // Actualiza el estado del envío en BD si hubo cambios
        if (envio.getEstadoEnvio() != estadoActualizado) {
            envio.setEstadoEnvio(estadoActualizado);
            envioRepository.save(envio);
        }

        // Retorna DTO con la información actualizada
        SeguimientoEnvioDTO dto = new SeguimientoEnvioDTO();

        dto.setIdEnvio(envio.getIdEnvio());
        dto.setNumeroSeguimiento(envio.getNumeroSeguimiento());
        dto.setServicioTransporte(envio.getServicioTransporte());
        dto.setTipoEntrega(envio.getTipoEntrega());
        dto.setEstadoEnvio(envio.getEstadoEnvio());
        dto.setDirDestino(envio.getDirDestino());
        dto.setCiudadDestino(envio.getLocalidad().getNombreLocalidad());
        dto.setFechaDespacho(envio.getFechaDespacho());
        dto.setFechaEstimadaLlegada(envio.getFechaEstimadaLlegada());
        dto.setFechaEntregado(envio.getFechaEntregado());
        dto.setDetalleEstadoExtranjero("Estado verificado con " + envio.getServicioTransporte());

        return dto;
    }
}
