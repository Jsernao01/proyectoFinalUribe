package com.example.EcomerceUribe.modelos.mapas;

import com.example.EcomerceUribe.modelos.DTOS.PedidoDTO;
import com.example.EcomerceUribe.modelos.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IProductoMapa.class})
public interface IPedidoMapa {

    @Mapping(source = "cliente.id", target = "clienteId")
    PedidoDTO convertir_pedido_a_pedidodto(Pedido pedido);

    List<PedidoDTO> convertir_lista_pedido_a_listapedidodto(List<Pedido> pedidos);
}
