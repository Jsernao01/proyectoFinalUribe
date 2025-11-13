package com.example.EcomerceUribe.controladores;

import com.example.EcomerceUribe.modelos.DTOS.PedidoDTO;
import com.example.EcomerceUribe.modelos.Pedido;
import com.example.EcomerceUribe.servicios.PedidoServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Controlador para operaciones en la tabla pedidos")
public class PedidoControlador {

    @Autowired
    private PedidoServicio servicio;

    @Operation(summary = "Crear un pedido en la BD")
    @PostMapping(produces = "application/json")
    public ResponseEntity<PedidoDTO> guardar(@RequestBody Pedido pedido) {
        PedidoDTO respuesta = this.servicio.guardarPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @Operation(summary = "Listar todos los pedidos")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<PedidoDTO>> listar() {
        List<PedidoDTO> respuesta = this.servicio.listarPedidos();
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @Operation(summary = "Buscar un pedido por id")
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Integer id) {
        PedidoDTO respuesta = this.servicio.buscarPedidoPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @Operation(summary = "Eliminar un pedido")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        this.servicio.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualizar un pedido")
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<PedidoDTO> actualizar(@PathVariable Integer id, @RequestBody Pedido datos) {
        PedidoDTO respuesta = this.servicio.actualizarPedido(id, datos);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}
