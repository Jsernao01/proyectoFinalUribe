package com.example.EcomerceUribe.controladores;

import com.example.EcomerceUribe.modelos.DTOS.EmpleadoActualizarDTO;
import com.example.EcomerceUribe.modelos.DTOS.EmpleadoCrearDTO;
import com.example.EcomerceUribe.modelos.DTOS.EmpleadoDTO;
import com.example.EcomerceUribe.servicios.EmpleadoServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@Tag(name = "Controlador para operaciones en la tabla empleados")
public class EmpleadoControlador {

    @Autowired
    private EmpleadoServicio servicio;

    @Operation(summary = "Crear un empleado en la BD")
    @PostMapping(produces = "application/json")
    public ResponseEntity<EmpleadoDTO> guardar(@RequestBody EmpleadoCrearDTO datos) {
        EmpleadoDTO respuesta = this.servicio.crearEmpleado(datos);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @Operation(summary = "Listar todos los empleados guardados en la BD")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<EmpleadoDTO>> listar() {
        List<EmpleadoDTO> respuesta = this.servicio.listarEmpleados();
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @Operation(summary = "Buscar un empleado en la BD por su identificador")
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<EmpleadoDTO> buscarPorId(@PathVariable Integer id) {
        EmpleadoDTO respuesta = this.servicio.buscarEmpleadoPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @Operation(summary = "Eliminar un empleado en la BD")
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        this.servicio.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Modificar cargo, salario y sede de un empleado en la BD")
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<EmpleadoDTO> modificar(@PathVariable Integer id, @RequestBody EmpleadoActualizarDTO datos) {
        EmpleadoDTO respuesta = this.servicio.actualizarEmpleado(id, datos);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}
