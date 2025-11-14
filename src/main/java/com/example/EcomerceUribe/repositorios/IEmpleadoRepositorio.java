package com.example.EcomerceUribe.repositorios;

import com.example.EcomerceUribe.modelos.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEmpleadoRepositorio extends JpaRepository<Empleado, Integer> {

    Optional<Empleado> findByUsuarioId(Integer usuarioId);
}
