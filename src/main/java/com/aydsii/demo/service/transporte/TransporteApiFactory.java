package com.aydsii.demo.service.transporte;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.aydsii.demo.enums.ServicioTransporte;
import com.aydsii.demo.exception.BusinessException;

/**
 * Factoría que implementa el patrón Factory Method en combinación con el patrón Strategy.
 * Registra automáticamente todos los beans que implementan TransporteApiClient
 * y resuelve la instancia correspondiente según el proveedor seleccionado.
 */
@Component
public class TransporteApiFactory {

    // Mapa en memoria que asocia cada ServicioTransporte con su implementación concreta
    private final Map<ServicioTransporte, TransporteApiClient> clients;

    // Constructor con inyección automática de Spring.
    public TransporteApiFactory(List<TransporteApiClient> apiClientList) {
        this.clients = apiClientList.stream()
                .collect(Collectors.toMap(TransporteApiClient::getServicioTransporte, Function.identity()));
    }

    // Devuelve el cliente de transporte adecuado según el servicio indicado
    public TransporteApiClient getClient(ServicioTransporte servicio) {
        TransporteApiClient client = clients.get(servicio);
        if (client == null) {
            throw new BusinessException("Servicio de transporte no soportado: " + servicio);
        }
        return client;
    }
}