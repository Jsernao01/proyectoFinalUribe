package com.example.EcomerceUribe.repositorios;

import com.example.EcomerceUribe.modelos.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPedidoRepositorio extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByClienteId(Integer clienteId);
}
