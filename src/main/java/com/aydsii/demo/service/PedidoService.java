package com.aydsii.demo.service;

import com.aydsii.demo.dto.ComprarDTO;
import com.aydsii.demo.exception.BusinessException;
import com.aydsii.demo.model.Carrito;
import com.aydsii.demo.model.ItemCarrito;
import com.aydsii.demo.model.Producto;
import com.aydsii.demo.model.Cliente;
import com.aydsii.demo.model.Nivel;
import com.aydsii.demo.model.Pedido;
import com.aydsii.demo.enums.EstadoPedido;
import com.aydsii.demo.model.Envio;
import com.aydsii.demo.repository.EnvioRepository;
import com.aydsii.demo.repository.PedidoRepository;
import com.aydsii.demo.service.transporte.TransporteApiClient;
import com.aydsii.demo.service.transporte.TransporteApiFactory;
import com.aydsii.demo.repository.CarritoRepository;
import com.aydsii.demo.model.Localidad;
import com.aydsii.demo.repository.LocalidadRepository;
import org.springframework.stereotype.Service;
import com.aydsii.demo.dto.PedidoDTO;
import com.aydsii.demo.model.DetallePedido;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {
    private final CarritoRepository carritoRepository;
    private final LocalidadRepository localidadRepository;
    private final PedidoRepository pedidoRepository;
    private final EnvioRepository envioRepository;
    private final TransporteApiFactory transporteApiFactory; // Inyección de la factoría

    public PedidoService(CarritoRepository carritoRepository, LocalidadRepository localidadRepository, PedidoRepository pedidoRepository, EnvioRepository envioRepository, TransporteApiFactory transporteApiFactory) {
        this.carritoRepository = carritoRepository;
        this.localidadRepository = localidadRepository;
        this.pedidoRepository = pedidoRepository;
        this.envioRepository = envioRepository;
        this.transporteApiFactory = transporteApiFactory;
    }

    
    //caso de uso comprar carrito
    
    @Transactional
    public PedidoDTO procesarCompra(ComprarDTO comprarDTO) {
        //busca el carrito por su id
        Carrito carrito = carritoRepository.findById(comprarDTO.getIdCarrito())
                .orElseThrow(() -> new BusinessException("Carrito no encontrado", HttpStatus.NOT_FOUND));

        // verifica que el carrito no este vacio
        if (carrito.estaVacio()) {
            throw new BusinessException("El carrito está vacío");
        }
        //obtiene los items del caerrito
        List<ItemCarrito> items = carrito.getItems();

        //bucle que verifica que todos los productos tengan stock suficiente para la compra
        for(ItemCarrito item : items){
            Producto producto = item.getProducto();

            if (!producto.alcanzaStock(item.getCantidad())) {
                throw new BusinessException("Stock insuficiente para el producto: " + producto.getNombre());
            }
        }

        //ahora se crena los detalles pedido uno por cada item del carrito ademas aprovechamos esta busqueda para crear variables necesarias para el pedido
        float subTotal = 0;
        int cantidadProductos = 0;
        float impactoAmbientalTotal = 0;
        int puntosTotales = 0;
        List<DetallePedido> detalles = new ArrayList<>();

        for( ItemCarrito item : items ){
            float subTotalItem = item.calcularSubTotal();
            int cantidadItem = item.getCantidad();

            subTotal = subTotal + subTotalItem;
            cantidadProductos = cantidadProductos + cantidadItem;

            float impactoAmbientalItem = item.calcularImpactoAmbientalItem();
            impactoAmbientalTotal = impactoAmbientalTotal + impactoAmbientalItem;

            //ahora se crea el detalle del item
            DetallePedido detalle = new DetallePedido(cantidadItem, subTotalItem,impactoAmbientalItem,item.getProducto());
            //lo agrega al arreglo de detalles pedidos
            detalles.add(detalle);

            int puntosItem = item.getProducto().getPuntos();                   // 1.1.10
            puntosTotales = puntosTotales + puntosItem;
        }

        //obtine al cliente que realiza el pedido
        Cliente cliente = carrito.getCliente();
        //ahora el nivel que posee el cliente para asi poder conseguir y aplicar los descunetos de pedido y envio
        Nivel nivel = cliente.getNivel();

        float descuentoPedidoCli = nivel.getDescuentoPedido();
        float descuentoEnvioCli = nivel.getDescuentoEnvio();
        //con todo ya conseguiro ahora se crea el pedido
        Pedido pedido = new Pedido(cliente, detalles, cantidadProductos, subTotal, impactoAmbientalTotal, puntosTotales, descuentoEnvioCli, descuentoPedidoCli);

        //ahora se busca la distancia de km de la localidad y se le envia al pedido para qwue calculo el importe

        // busca la localidad por el nombre que llegó en el DTO
        Localidad localidad = localidadRepository
            .findByNombreLocalidadIgnoreCase(comprarDTO.getLocalidad())
            .orElseThrow(() -> new BusinessException(
            "Localidad no encontrada: " + comprarDTO.getLocalidad(), HttpStatus.NOT_FOUND));

        // calcula el costo de envío con la distancia y el tipo de entrega
        pedido.calcularCostoEnvio(localidad.getDistanciaKm(), comprarDTO.getTipoEntrega(),comprarDTO.getServicioTransporte());
        

        //ahora se llma a calcularTotal para que el pedido calcule intenmente su total aplicasndole descuentos a el subtotal y el envio
        pedido.calcularTotal();

        //con todo ya terminado se cambia el estado al pedido a CONFIRMADO
        pedido.cambiarEstado(EstadoPedido.CONFIRMADO);

        //se le suman los puntos de la compra al cliente
        cliente.sumarPuntos(puntosTotales);

        //se descuneta el stock a los productos comprados
        for(DetallePedido detalle : detalles){
            Producto producto = detalle.getProducto();
            int cantidad = detalle.getCantidad();
            producto.descontarStock(cantidad);
        }

        //con todo hecho se guarda el pedido en la base de datos
        pedidoRepository.save(pedido);
        //como Pedido tiene detalles con cascade= ALL los pedidos se guardan tambine en la base de datos sin que se tengan que gguardar uno por uno

        //se crea el envio asociado
        Envio envio = new Envio(pedido, localidad, comprarDTO.getTipoEntrega(), comprarDTO.getDirDestino(), comprarDTO.getServicioTransporte());
        
        // 1. Se obtiene el cliente del servicio de transporte seleccionado (Andreani o Correo Argentino)
        TransporteApiClient apiClient = transporteApiFactory.getClient(comprarDTO.getServicioTransporte());

        // 2. Se pide al servicio de transporte que genere el código de seguimiento
        Long numeroSeguimientoGenerado = apiClient.generarNumeroSeguimiento();

        // 3. Setea los datos correspondientes en el objeto Envio
        envio.setNumeroSeguimiento(numeroSeguimientoGenerado);
        
        //y se guarda tambine
        envioRepository.save(envio);

        //por ultimo se borra el carrito
        carritoRepository.deleteById(comprarDTO.getIdCarrito());

        //con todo ya terminado se envia los datos del pedido al usuario
        return new PedidoDTO(pedido);
    }
}
