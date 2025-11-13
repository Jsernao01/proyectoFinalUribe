package com.example.EcomerceUribe.controladores;

import com.example.EcomerceUribe.modelos.Cliente;
import com.example.EcomerceUribe.modelos.DTOS.ClienteDTO;
import com.example.EcomerceUribe.servicios.ClienteServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Controlador para operaciones en la tabla clientes")
public class ClienteControlador {

    @Autowired
    private ClienteServicio servicio;

    @Operation(summary = "Crear un cliente en la BD")
    @PostMapping(produces = "application/json")
    public ResponseEntity<ClienteDTO> guardar(@RequestBody Cliente cliente) {
        ClienteDTO respuesta = this.servicio.guardarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @Operation(summary = "Listar todos los clientes")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ClienteDTO>> listar() {
        List<ClienteDTO> respuesta = this.servicio.listarClientes();
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @Operation(summary = "Buscar un cliente por id")
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Integer id) {
        ClienteDTO respuesta = this.servicio.buscarClientePorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @Operation(summary = "Eliminar un cliente")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        this.servicio.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualizar los datos de un cliente")
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ClienteDTO> actualizar(@PathVariable Integer id, @RequestBody Cliente datos) {
        ClienteDTO respuesta = this.servicio.actualizarCliente(id, datos);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}
