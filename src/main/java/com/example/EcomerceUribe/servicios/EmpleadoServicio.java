package com.example.EcomerceUribe.servicios;

import com.example.EcomerceUribe.ayudas.Cargos;
import com.example.EcomerceUribe.ayudas.Sedes;
import com.example.EcomerceUribe.modelos.DTOS.EmpleadoActualizarDTO;
import com.example.EcomerceUribe.modelos.DTOS.EmpleadoCrearDTO;
import com.example.EcomerceUribe.modelos.DTOS.EmpleadoDTO;
import com.example.EcomerceUribe.modelos.Empleado;
import com.example.EcomerceUribe.modelos.Usuario;
import com.example.EcomerceUribe.modelos.mapas.IEmpleadoMapa;
import com.example.EcomerceUribe.repositorios.IEmpleadoRepositorio;
import com.example.EcomerceUribe.repositorios.IUsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoServicio {

    @Autowired
    private IEmpleadoRepositorio repositorio;

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    @Autowired
    private IEmpleadoMapa mapa;

    public EmpleadoDTO crearEmpleado(EmpleadoCrearDTO datosEmpleado) {
        validarDatosBasicos(datosEmpleado.getCargo(), datosEmpleado.getSalario(), datosEmpleado.getSede());

        if (datosEmpleado.getUsuarioId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El identificador del usuario es obligatorio"
            );
        }

        Optional<Usuario> usuarioAsociado = this.usuarioRepositorio.findById(datosEmpleado.getUsuarioId());
        if (!usuarioAsociado.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No existe un usuario con el id " + datosEmpleado.getUsuarioId()
            );
        }

        if (this.repositorio.findByUsuarioId(datosEmpleado.getUsuarioId()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El usuario ya tiene un empleado asociado"
            );
        }

        Empleado empleadoAGuardar = new Empleado();
        empleadoAGuardar.setCargo(datosEmpleado.getCargo());
        empleadoAGuardar.setSalario(datosEmpleado.getSalario());
        empleadoAGuardar.setSede(datosEmpleado.getSede());
        empleadoAGuardar.setUsuario(usuarioAsociado.get());

        Empleado empleadoGuardado = this.repositorio.save(empleadoAGuardar);
        if (empleadoGuardado == null) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "No fue posible guardar el empleado"
            );
        }

        return this.mapa.convertirEmpleadoAEmpleadoDto(empleadoGuardado);
    }

    public List<EmpleadoDTO> listarEmpleados() {
        List<Empleado> empleados = this.repositorio.findAll();
        return this.mapa.convertirListaEmpleadoAListaDto(empleados);
    }

    public EmpleadoDTO buscarEmpleadoPorId(Integer id) {
        Empleado empleado = obtenerEmpleadoPorId(id);
        return this.mapa.convertirEmpleadoAEmpleadoDto(empleado);
    }

    public void eliminarEmpleado(Integer id) {
        Empleado empleado = obtenerEmpleadoPorId(id);
        try {
            this.repositorio.delete(empleado);
        } catch (Exception error) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "No fue posible eliminar el empleado, " + error.getMessage()
            );
        }
    }

    public EmpleadoDTO actualizarEmpleado(Integer id, EmpleadoActualizarDTO datosActualizados) {
        Empleado empleado = obtenerEmpleadoPorId(id);

        validarDatosBasicos(datosActualizados.getCargo(), datosActualizados.getSalario(), datosActualizados.getSede());

        empleado.setCargo(datosActualizados.getCargo());
        empleado.setSalario(datosActualizados.getSalario());
        empleado.setSede(datosActualizados.getSede());

        Empleado empleadoActualizado = this.repositorio.save(empleado);
        if (empleadoActualizado == null) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "No fue posible actualizar el empleado"
            );
        }

        return this.mapa.convertirEmpleadoAEmpleadoDto(empleadoActualizado);
    }

    private Empleado obtenerEmpleadoPorId(Integer id) {
        Optional<Empleado> empleadoBuscado = this.repositorio.findById(id);
        if (!empleadoBuscado.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No se encontró un empleado con el id " + id
            );
        }
        return empleadoBuscado.get();
    }

    private void validarDatosBasicos(Cargos cargo, Integer salario, Sedes sede) {
        if (cargo == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El cargo del empleado es obligatorio"
            );
        }
        if (salario == null || salario <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El salario del empleado debe ser un valor positivo"
            );
        }
        if (sede == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La sede del empleado es obligatoria"
            );
        }
    }
}
