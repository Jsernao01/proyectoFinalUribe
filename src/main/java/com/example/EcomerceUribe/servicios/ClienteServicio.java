package com.example.EcomerceUribe.servicios;

import com.example.EcomerceUribe.modelos.Cliente;
import com.example.EcomerceUribe.modelos.DTOS.ClienteDTO;
import com.example.EcomerceUribe.modelos.mapas.IClienteMapa;
import com.example.EcomerceUribe.repositorios.IClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServicio {

    @Autowired
    private IClienteRepositorio repositorio;

    @Autowired
    private IClienteMapa mapa;

    public ClienteDTO guardarCliente(Cliente cliente) {
        if (cliente.getDireccion() == null || cliente.getDireccion().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La dirección del cliente es obligatoria");
        }
        if (cliente.getReferenciaPago() == null || cliente.getReferenciaPago().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La referencia de pago es obligatoria");
        }
        if (cliente.getDepartamento() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El departamento del cliente es obligatorio");
        }
        if (cliente.getCiudad() == null || cliente.getCiudad().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La ciudad del cliente es obligatoria");
        }
        if (cliente.getCalificacion() != null && (cliente.getCalificacion() < 0 || cliente.getCalificacion() > 5)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La calificación debe estar entre 0 y 5");
        }

        Cliente clienteGuardado = this.repositorio.save(cliente);
        if (clienteGuardado == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo guardar el cliente");
        }
        return this.mapa.convertir_cliente_a_clientedto(clienteGuardado);
    }

    public List<ClienteDTO> listarClientes() {
        List<Cliente> clientes = this.repositorio.findAll();
        return this.mapa.convertir_lista_cliente_a_listaclientedto(clientes);
    }

    public ClienteDTO buscarClientePorId(Integer id) {
        Optional<Cliente> cliente = this.repositorio.findById(id);
        if (cliente.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el cliente con id " + id);
        }
        return this.mapa.convertir_cliente_a_clientedto(cliente.get());
    }

    public void eliminarCliente(Integer id) {
        Optional<Cliente> cliente = this.repositorio.findById(id);
        if (cliente.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el cliente con id " + id);
        }
        try {
            this.repositorio.delete(cliente.get());
        } catch (Exception error) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "No se pudo eliminar el cliente: " + error.getMessage());
        }
    }

    public ClienteDTO actualizarCliente(Integer id, Cliente datosActualizados) {
        Optional<Cliente> clienteBusqueda = this.repositorio.findById(id);
        if (clienteBusqueda.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el cliente con id " + id);
        }
        Cliente clienteEncontrado = clienteBusqueda.get();

        if (datosActualizados.getDireccion() != null && !datosActualizados.getDireccion().isBlank()) {
            clienteEncontrado.setDireccion(datosActualizados.getDireccion());
        }
        if (datosActualizados.getCalificacion() != null) {
            if (datosActualizados.getCalificacion() < 0 || datosActualizados.getCalificacion() > 5) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La calificación debe estar entre 0 y 5");
            }
            clienteEncontrado.setCalificacion(datosActualizados.getCalificacion());
        }
        if (datosActualizados.getReferenciaPago() != null && !datosActualizados.getReferenciaPago().isBlank()) {
            clienteEncontrado.setReferenciaPago(datosActualizados.getReferenciaPago());
        }
        if (datosActualizados.getDepartamento() != null) {
            clienteEncontrado.setDepartamento(datosActualizados.getDepartamento());
        }
        if (datosActualizados.getCiudad() != null && !datosActualizados.getCiudad().isBlank()) {
            clienteEncontrado.setCiudad(datosActualizados.getCiudad());
        }

        Cliente clienteActualizado = this.repositorio.save(clienteEncontrado);
        if (clienteActualizado == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "No se pudo actualizar el cliente, intenta nuevamente");
        }
        return this.mapa.convertir_cliente_a_clientedto(clienteActualizado);
    }
}
