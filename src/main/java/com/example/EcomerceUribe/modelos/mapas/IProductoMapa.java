package com.example.EcomerceUribe.modelos.mapas;

import com.example.EcomerceUribe.modelos.DTOS.ProductoDTO;
import com.example.EcomerceUribe.modelos.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IProductoMapa {

    @Mapping(source = "pedido.id", target = "pedidoId")
    ProductoDTO convertir_producto_a_productodto(Producto producto);

    List<ProductoDTO> convertir_lista_producto_a_listaproductodto(List<Producto> productos);
}
