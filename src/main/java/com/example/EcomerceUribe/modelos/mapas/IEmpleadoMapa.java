package com.example.EcomerceUribe.modelos.mapas;

import com.example.EcomerceUribe.modelos.DTOS.EmpleadoDTO;
import com.example.EcomerceUribe.modelos.Empleado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IEmpleadoMapa {

    @Mapping(source = "usuario.id", target = "usuarioId")
    EmpleadoDTO convertirEmpleadoAEmpleadoDto(Empleado empleado);

    List<EmpleadoDTO> convertirListaEmpleadoAListaDto(List<Empleado> empleados);
}
