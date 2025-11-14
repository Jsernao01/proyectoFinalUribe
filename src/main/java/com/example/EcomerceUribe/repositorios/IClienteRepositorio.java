package com.example.EcomerceUribe.repositorios;

import com.example.EcomerceUribe.modelos.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClienteRepositorio extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByCiudad(String ciudad);
}
