package com.example.EcomerceUribe.repositorios;

import com.example.EcomerceUribe.modelos.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductoRepositorio extends JpaRepository<Producto, Integer> {

    List<Producto> findByPedidoId(Integer pedidoId);
}
