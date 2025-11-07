package com.example.EcomerceUribe.repositorios;

import com.example.EcomerceUribe.modelos.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUsuarioRepositorio extends JpaRepository<Usuario,Integer> {

    //GUARDAR
    //EDITAR POR ID
    //ELIMINAR POR ID
    //BUSCAR POR ID
    //BUSCAR TODOS LOS REGISTROS

    //SECCION DE CONSULTAS O QUERIES PERSONALIZADAS
    List<Usuario> findByNombres(String nombres);
    Optional<Usuario> findByCorreo(String correo);

}
