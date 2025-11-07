package com.example.EcomerceUribe.controladores;

import com.example.EcomerceUribe.modelos.DTOS.UsuarioGenericoDTO;
import com.example.EcomerceUribe.modelos.Usuario;
import com.example.EcomerceUribe.servicios.UsuarioServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name="Controlador para operaciones en la tabla usuarios")
public class UsuarioControlador {

    //1. Llamar al servicio
    @Autowired
    UsuarioServicio servicio;

    //2. Listar los posibles llamados a los servicios disponibles
    //3. se crean funciones por cada posible llamado y se les agrega
    //el metodo HTTP correspondiente (GET/PUT/POST/DELETE)

    //guardar
    @Operation(summary = "Crear un usuario en la BD")
    @PostMapping(produces = "application/json")
    public ResponseEntity<UsuarioGenericoDTO>guardar(@RequestBody Usuario datos){
        UsuarioGenericoDTO respuesta=this.servicio.guardarUsuariogenerico(datos);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    //listarTodos
    @Operation(summary = "Listar todos los usuarios guardados en la BD")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UsuarioGenericoDTO>>listar(){
        List<UsuarioGenericoDTO> respuesta=this.servicio.buscarTodosLosUsuarios();
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    //buscarPorId
    @Operation(summary = "Buscar un usuario en la BD")
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UsuarioGenericoDTO>buscarPorId(@PathVariable Integer id){
        UsuarioGenericoDTO respuesta=this.servicio.buscarUsuarioGenericoPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    //eliminar
    @Operation(summary = "Eliminar un usuario en la BD")
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Void>eliminar(@PathVariable Integer id){
        this.servicio.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    //modificar
    @Operation(summary = "Modifica nombre y correo de un usuario en la BD")
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UsuarioGenericoDTO> modificar(@PathVariable Integer id,@RequestBody Usuario datos){
        UsuarioGenericoDTO respuesta=this.servicio.actualizarUsuario(id,datos);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

}
