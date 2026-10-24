package com.aydsii.demo.controller;

import com.aydsii.demo.dto.ApiResponse;
import com.aydsii.demo.dto.ComprarDTO;
import com.aydsii.demo.dto.PedidoDTO;
import org.springframework.http.HttpStatus;
import com.aydsii.demo.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedido")
@Tag(name = "Pedido", description = "Compra de los productos del carrito")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/comprar")
    @Operation(summary = "Comprar carrito",
            description = "Valida el carrito y registra el pedido")
    public ResponseEntity<ApiResponse<PedidoDTO>> crearPedido(@Valid @RequestBody ComprarDTO comprarDTO) {
        PedidoDTO pedidoCreado = pedidoService.procesarCompra(comprarDTO);

        //la respuesta con el pedido ya creado 
        ApiResponse<PedidoDTO> response = ApiResponse.success(HttpStatus.CREATED.value(), "Su compra se realizo con exito" , pedidoCreado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
