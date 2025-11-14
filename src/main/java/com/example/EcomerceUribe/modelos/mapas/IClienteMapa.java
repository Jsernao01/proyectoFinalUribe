package com.example.EcomerceUribe.modelos.mapas;

import com.example.EcomerceUribe.modelos.Cliente;
import com.example.EcomerceUribe.modelos.DTOS.ClienteDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IClienteMapa {

    ClienteDTO convertir_cliente_a_clientedto(Cliente cliente);

    List<ClienteDTO> convertir_lista_cliente_a_listaclientedto(List<Cliente> clientes);
}
